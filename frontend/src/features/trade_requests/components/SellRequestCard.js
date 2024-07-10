import React from 'react';
import RequestCard from './RequestCard';

const SellRequestCard = ({tradeRequest}) => {
  return (
    <RequestCard 
      tradeRequest={tradeRequest}
      isBuyRequest={false}
      productUser={{
        name: tradeRequest.sellerName,
        joinedAt: tradeRequest.sellerJoinedAt
      }} >
    </RequestCard>
  );
}

export default SellRequestCard;