import React, { useEffect, useState } from "react";
import axios from "axios";
import Header from "../../components/AppHeader";
import { useNavigate } from "react-router-dom";
import DataNotFound from "../../components/DataNotFound";
import Loader from "../../components/Loader"

export default function PurchaseHistory() {
  const navigate = useNavigate();
  const userid = 1;

  const [purchaseHistory, setPurchaseHistory] = useState([]);
  const [loading, setLoading] = useState(false); // Add loading state

  useEffect(() => {
    const fetchPurchasedHistory = async () => {
      try {
        setLoading(true); // Set loading to true before fetching
        const params = new URLSearchParams(window.location.search);
        const productId = params.get("id");
        console.log({ productId });
        const response = await axios.get(`http://localhost:8080/purchased_products/${userid}`, {
          params: params,
          paramsSerializer: { indexes: null }
        });
        const data = response.data;
        setPurchaseHistory(data);
        console.log(data, "purchased_products");
      } catch (error) {
        console.error("Failed to fetch product data", error);
      } finally {
         setTimeout(()=>{
          setLoading(false)
         },800)
        ; // Set loading to false after fetching
      }
    };
    fetchPurchasedHistory();
  }, []);

  return (
    <>
      <div className="bg-gray-100 dark:bg-gray-950 py-8 h-full h-screen max-h-100">
        <Header />
        <div className="py-2 px-4">
          <button
            type="button"
            className="text-white bg-black border border-gray-300 focus:outline-none hover:bg-white hover:text-black focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 my-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
            onClick={() => {
              navigate("/profile");
            }}
          >
            Back to Profile
          </button>
        </div>
        <div className="flex items-center justify-between mb-4 px-4">
          <h3 className="text-lg font-semibold dark:text-white">
            Purchase History
          </h3>
          {purchaseHistory.length === 0 ? '' : <button className="dark:text-white" size="sm" variant="link">
            View More
          </button>}
        </div>

        {loading ? ( // Conditionally render the loading indicator
          <div className="my-50">
           <Loader/>
          </div>
        ) : purchaseHistory.length === 0 ? (
          <div className="my-20">
            <DataNotFound message={"Sorry, you have not purchased any items."} />
          </div>
        ) : (
          <div className="border rounded-lg shadow-sm dark:border-gray-800 mx-4">
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 dark:text-white px-4">
              <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                  <th className="px-6 py-3">No.</th>
                  <th className="px-6 py-3">Title</th>
                  <th className="px-6 py-3">Category</th>
                  <th className="px-6 py-3">Order Status</th>
                  <th className="px-6 py-3">Price</th>
                </tr>
              </thead>
              <tbody>
                {purchaseHistory.map((item, index) => (
                  <tr key={index} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                    <td className="px-6 py-4">{index + 1}</td>
                    <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                      <div className="flex items-center gap-3">
                        <div>{item?.title}</div>
                      </div>
                    </td>
                    <td className="px-6 py-4">{item?.category}</td>
                    <td className="px-6 py-4">{item?.orderStatus}</td>
                    <td className="px-6 py-4">{item?.totalAmount}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </>
  );
}
