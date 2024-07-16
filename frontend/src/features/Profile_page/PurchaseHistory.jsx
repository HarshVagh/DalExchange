import React, { useEffect, useState } from "react";
import axios from "axios";
import Header from "../../components/Header";
import DataNotFound from "../../components/DataNotFound";
import Loader from "../../components/Loader"
import SubHeader from "../../components/SubHeader";
import ErrorAlert from "../../components/ErrorAlert";

export default function PurchaseHistory() {
  const userid = 1;

  const [purchaseHistory, setPurchaseHistory] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null); 

  const headerConfig = {
    search: false,
    requests: true,
    notifications: true,
    add: true,
    profile: true
  };

  useEffect(() => {
    const fetchPurchasedHistory = async () => {
      try {
        setIsLoading(true);
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
        setError(error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchPurchasedHistory();
  }, []);

  return (
    <>
      <div className="bg-gray-100 dark:bg-gray-950 py-8 h-full h-screen max-h-100">
        <Header config={headerConfig} />
        <SubHeader title={'Purchase History'} backPath={'/profile'} />
        {isLoading && <Loader title={'Loading Profile Details...'} />}
        {!isLoading && error && <ErrorAlert message={error.message} />}
        {!isLoading && !error && purchaseHistory && purchaseHistory.length >0 ?(
        <div className="py-8 mt-50">
        <div className="border rounded-lg shadow-sm dark:border-gray-800 ">
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 dark:text-white">
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
        </div>
        ) : (
          <div className="my-20">
            <DataNotFound message={"Oops! No items sold yet."} />
          </div> )}
      </div>
    </>
  );
}
