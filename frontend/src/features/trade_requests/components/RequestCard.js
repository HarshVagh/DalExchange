import React from 'react';
import placeholder from "../../../assets/images/placeholder.png";
import UserPlaceholder from "../../../assets/images/placeholder-user.jpg";

const RequestCard = ({tradeRequest}) => {
  return (
    tradeRequest && tradeRequest.product &&
    <div key={tradeRequest.requestId} className="flex items-center mb-4 bg-white border border-gray-200 rounded-lg shadow md:flex-row hover:bg-gray-100">
      <img 
        className="basis-1/6 object-cover w-full h-full rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-s-lg"
        src={placeholder} alt=""/>
      <div className="flex flex-col basis-5/6 justify-between p-4 leading-normal">
        <div className="flex justify-between">
          <div>
            <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900">{tradeRequest.product.title}</h5>
            <p className="mb-3 font-normal text-gray-700 min-h-12">{tradeRequest.product.description}</p>
            <div className="flex items-center gap-4">
              <img className="w-10 h-10 rounded-full border-2" src={UserPlaceholder} alt=""/>
              <div className="font-medium">
                  <div>{tradeRequest.buyerName}</div>
                  <div className="text-sm text-gray-500">Joined in August 2014</div>
              </div>
              <div className="text-gray-800 text-sm font-medium ml-4">
                <span className="bg-gray-100 h-6 px-3 py-1 rounded-full border border-gray-400 mr-2">{tradeRequest.product.categoryName}</span>
                <span className="bg-gray-100 h-6 px-3 py-1 rounded-full border border-gray-400 mr-2">{tradeRequest.product.productCondition}</span>
                {tradeRequest.product.shippingType === 'free' && 
                  <span className="bg-gray-100 h-6 px-3 py-1 rounded-full border border-gray-400">Free Shipping</span>
                }
              </div>
            </div>
          </div>
          <div className="flex flex-col justify-between">
            <div className="flex justify-end">
              <div className="ml-2 px-3 py-2 rounded-lg border border-gray-300 max-h-24">
                <div className="flex flex-col items-center justify-end">
                  <dt className="mb-2 text-3xl font-medium">${tradeRequest.product.price}</dt>
                  <dd className="text-gray-800">Actual</dd>
                </div>
              </div>
              <div className="ml-2 px-3 py-2 rounded-lg border border-gray-300 max-h-24">
                <div className="flex flex-col items-center justify-end">
                  <dt className="mb-2 text-3xl font-medium">${tradeRequest.requestedPrice}</dt>
                  <dd className="text-gray-800">Requested</dd>
                </div>
              </div>
            </div>
            <div>
              <div className="flex justify-end gap-2 items-end w-full">
                <button type="button" 
                  className="text-white bg-gray-900 hover:bg-gray-800 focus:outline-none font-medium rounded-lg text-sm px-8 py-2.5">
                  Accept
                </button>
                <button 
                  type="button"
                  className="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 font-medium rounded-lg text-sm px-8 py-2.5">
                  Reject
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RequestCard;