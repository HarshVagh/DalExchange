import React, { useEffect, useState } from "react";
import Header from "../../components/Header";
import { TradeRequestApi } from "../../services/TradeRequestApi";
import RequestCard from "./components/RequestCard";
import SubHeader from "../../components/SubHeader";
import Loader from "../../components/Loader";

const TradeRequests = () => {
  const [activeTab, setActiveTab] = useState("buy");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const [buyRequests, setBuyRequests] = useState([])

  const [sellRequests, setSellRequests] = useState([])

  const headerConfig = {
    search: false,
    requests: true,
    notifications: true,
    add: true,
    profile: true
  };

  useEffect(() => {
    const fetchTradeRequests = async () => {
      const setters = {
        buyRequests: setBuyRequests,
        sellRequests: setSellRequests,
        isLoading: setIsLoading,
        error: setError
      };
      await TradeRequestApi.getTradeRequests(setters);
    };
    fetchTradeRequests();
  }, []);

  // const handleAcceptRequest = (requestType, requestId) => {
  //   if (requestType === "buy") {
  //     const updatedBuyRequests = buyRequests.map((request) => {
  //       if (request.id === requestId) {
  //         return { ...request, status: "accepted" }
  //       }
  //       return request
  //     })
  //     setBuyRequests(updatedBuyRequests)
  //   } else {
  //     const updatedSellRequests = sellRequests.map((request) => {
  //       if (request.id === requestId) {
  //         return { ...request, status: "accepted" }
  //       }
  //       return request
  //     })
  //     setSellRequests(updatedSellRequests)
  //   }
  // }

  // const handleDeclineRequest = (requestType, requestId) => {
  //   if (requestType === "buy") {
  //     const updatedBuyRequests = buyRequests.map((request) => {
  //       if (request.id === requestId) {
  //         return { ...request, status: "declined" }
  //       }
  //       return request
  //     })
  //     setBuyRequests(updatedBuyRequests)
  //   } else {
  //     const updatedSellRequests = sellRequests.map((request) => {
  //       if (request.id === requestId) {
  //         return { ...request, status: "declined" }
  //       }
  //       return request
  //     })
  //     setSellRequests(updatedSellRequests)
  //   }
  // }

  return (
    <div className="flex flex-col min-h-[100dvh]">
      <Header config={headerConfig}></Header>
      <SubHeader title={'Trade Requests'} backPath={'/products'}></SubHeader>
      {isLoading && <Loader title={'Loading Trade Requests...'} />}
      {!isLoading && error && <div className="flex justify-center h-16 w-full" >
          <div className="flex items-center py-4 px-12 mt-4 text-sm text-red-600 rounded-lg bg-red-50 border-2 border-red-600" role="alert">
            <span className="sr-only">Error</span>
            <div>
              <span className="font-medium">Error!</span> {error.message}
            </div>
          </div>
        </div>} 
      {!isLoading && !error && <main>
        <div className="w-full max-w-6xl mx-auto px-4 md:px-6 pb-8">
          <div>
          <div className="my-8">
            <ul className="text-sm font-medium text-center text-gray-500 rounded-lg shadow sm:flex">
              <li className="w-full focus-within:z-10" onClick={() => setActiveTab('buy')}>
                <div className={`inline-block w-full p-4 border-2 border-gray-900 rounded-s-lg focus:outline-none ${activeTab === 'buy' ? 'text-gray-100 bg-gray-900' : 'text-gray-900 bg-gray-100' }`}>
                  Buy Requests
                </div>
              </li>
              <li className="w-full focus-within:z-10" onClick={() => setActiveTab('sell')}>
                <div className={`inline-block w-full p-4 border-2 border-gray-900 rounded-e-lg focus:outline-none ${activeTab === 'sell' ? 'text-gray-100 bg-gray-900' : 'text-gray-900 bg-gray-100' }`}>
                  Sell Requests
                </div>
              </li>
            </ul>
          </div>
          {activeTab === 'buy' && buyRequests.map((tradeRequest) => (
            <RequestCard tradeRequest={tradeRequest}/>
          ))}
          {activeTab === 'sell' && sellRequests.map((tradeRequest) => (
            <RequestCard tradeRequest={tradeRequest}/>
          ))}
          </div>
        </div>
      </main>}
    </div>
  )
}

export default TradeRequests;