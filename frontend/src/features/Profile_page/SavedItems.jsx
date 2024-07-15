import React, { useEffect, useState } from "react";
import axios from "axios";
import Header from "../../components/Header";
import { useNavigate } from "react-router-dom";
import DataNotFound from "../../components/DataNotFound";
import toast from 'react-hot-toast';
import Loader from "../../components/Loader";
import SubHeader from "../../components/SubHeader";

export default function SavedItems() {
  const navigate = useNavigate();
  const userid = 1;
  const [loading, setLoading] = useState(false);
  const [savedProducts, setSavedProducts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProductId, setSelectedProductId] = useState(null);

  useEffect(() => {
    fetchedSavedProducts();
  }, []);

  const fetchedSavedProducts = async () => {
    try {
      const params = new URLSearchParams(window.location.search);
      const productId = params.get("id");
      setLoading(true);
      const response = await axios.get(
        `http://localhost:8080/saved_products/${userid}`,
        {
          params: params,
          paramsSerializer: { indexes: null },
        }
      );
      const data = response.data;
      setSavedProducts(data);
    } catch (error) {
      console.error("Failed to fetch product data", error);
    } finally {
      setTimeout(() => {
        setLoading(false);
      }, 800);
    }
  };

  const handleRemoveClick = (productId) => {
    setSelectedProductId(productId);
    setIsModalOpen(true);
  };

  const confirmRemoveFavorite = async (confirm) => {
    if (confirm) {
      await removeFavorite(userid, selectedProductId);
    }
    setIsModalOpen(false);
    setSelectedProductId(null);
  };

  async function removeFavorite(userid, productId) {
    const headers = {
      "Content-Type": "application/json",
    };
    const params = new URLSearchParams(window.location.search);

    const updatePromise = axios.post(
      `http://localhost:8080/${userid}/${productId}/removesaved`,
      {
        params: params,
        paramsSerializer: { indexes: null },
      }
    );

    toast.promise(
      updatePromise,
      {
        loading: "Updating SavedItems...",
        success: "SavedItems updated successfully!",
        error: "Failed to update SavedItems.",
      },
      {
        style: {
          minWidth: "250px",
        },
        success: {
          duration: 1000,
          icon: "👏",
        },
      }
    );

    try {
      const response = await updatePromise;
      const data = response;
      if (response.status === 200) {
        fetchedSavedProducts();
      }
      console.log(data.data, "profile details");
    } catch (error) {
      console.error("Failed to update profile", error);
    }
  }

  return (
    <>
      <div className="bg-gray-100 dark:bg-gray-950 py-8 h-screen max-h-100">
        <Header />
        <SubHeader title={'Saved Items'} backPath={'/profile'} />
        {loading ? (
          <div className="my-50">
            <Loader />
          </div>
        ) : savedProducts.length === 0 ? (
          <div className="my-20">
            <DataNotFound message={"Sorry, no saved items found."} />
          </div>
        ) : (
          <div className="border rounded-lg shadow-sm dark:border-gray-800 m-4">
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 dark:text-white">
              <thead className="px-4 text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                  <th className="px-6 py-3">Id</th>
                  <th className="px-6 py-3">Title</th>
                  <th className="px-6 py-3">Price</th>
                  <th className="px-6 py-3">Category</th>
                  <th className="px-6 py-3">Product Condition</th>
                  <th className="px-6 py-3">Use Duration</th>
                  <th className="px-6 py-3">Quantity Available</th>
                  <th className="px-6 py-3">Favorite</th>
                </tr>
              </thead>
              <tbody>
                {savedProducts.map((item, index) => (
                  <tr
                    className="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
                    key={index}
                  >
                    <td className="px-6 py-4">{index + 1}</td>
                    <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                      <div className="flex items-center gap-3">
                        <div>{item.title}</div>
                      </div>
                    </td>
                    <td className="px-6 py-4">{item.price}</td>
                    <td className="px-6 py-3">{item.category}</td>
                    <td className="px-6 py-3">{item.productCondition}</td>
                    <td className="px-6 py-3">{item.useDuration}</td>
                    <td className="px-6 py-3">{item.quantityAvailable}</td>
                    <td
                      className="px-6 py-3 cursor-pointer"
                      onClick={() => {
                        handleRemoveClick(item?.productId);
                      }}
                    >
                      <FilledHeartIcon />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
      {isModalOpen && (
        <ConfirmationModal
          message="Are you sure you want to remove this item from your favorites?"
          onConfirm={() => confirmRemoveFavorite(true)}
          onCancel={() => confirmRemoveFavorite(false)}
        />
      )}
    </>
  );
}

const HeartIcon = (props) => {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z" />
    </svg>
  );
};

const FilledHeartIcon = (props) => {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="currentColor"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z" />
    </svg>
  );
};

const ConfirmationModal = ({ message, onConfirm, onCancel }) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-white p-6 rounded-md shadow-md">
        <p className="mb-4">{message}</p>
        <div className="flex justify-end">
          <button
            onClick={onConfirm}
            className="bg-red-500 text-white px-4 py-2 rounded-md mr-2"
          >
            Confirm
          </button>
          <button
            onClick={onCancel}
            className="bg-gray-300 px-4 py-2 rounded-md"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};
