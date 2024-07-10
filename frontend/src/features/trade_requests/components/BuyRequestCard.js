import React from 'react';
import RequestCard from './RequestCard';

const BuyRequestCard = ({tradeRequest}) => {
  return (
    <RequestCard 
      tradeRequest={tradeRequest}
      isBuyRequest={true}
      productUser={{
        name: tradeRequest.buyerName,
        joinedAt: tradeRequest.buyerJoinedAt
      }} >
    </RequestCard>
  );
}

export default BuyRequestCard;