import AxiosInstance from "./AxiosInstance";

export const TradeRequestApi = {
  getTradeRequests: async (setters) => {
    try {
      setters.isLoading(true);
      setters.error(null);
      const sellResponse = await AxiosInstance.get("/sell_requests");
      const buyResponse = await AxiosInstance.get("/buy_requests");
      setters.sellRequests(sellResponse.data);
      setters.buyRequests(buyResponse.data);
    } catch (error) {
      console.error("Error fetching trade requests: ", error);
      setters.error(error);
    } finally {
      setters.isLoading(false);
    }
  },
  updateStatus: async (id, status) => {
    try {
      await AxiosInstance.put("/update_trade_status/" + id, null, {
        params: {
          status: status
        }
      });
    } catch (error) {
      console.error("Error updating trade request status: ", error);
      alert("Error updating trade request status!");
    }
  },
  create: async (body) => {
    try {
      const response = await AxiosInstance.post("/create_trade_request", body);
      console.log("Trade request created successfully:", response.data);
    } catch (error) {
      console.error("Error creating trade request:", error);
    }
  },
  createPaymentIntent: async (amount, productId, userId, requestId) => {
    try {
      const response = await axios.post(`${BASE_URL}/api/payment/create-payment-intent`, null, {
        params: { amount, productId, userId, requestId }
      });
      return response.data;
    } catch (error) {
      console.error('Error in createPaymentIntent:', error.response ? error.response.data : error.message);
      throw error;
    }
  },



  acceptBuyRequest: async () => {
    try {
      // Send a POST request to create a payment intent with the necessary parameters
      const response = await axios.post(`${BASE_URL}/api/payment/create-payment-intent`, null, {
    
      });
      
      // Assuming the response contains a sessionId
      const { sessionId } = response.data;

      // Handle the sessionId, e.g., use it to redirect to a checkout page
      console.log("Payment initiation successful! Session ID:", sessionId);

      // Optionally update the status of the trade request after initiating payment
      // You may need to update this according to your application's logic
      // await TradeRequestApi.updateStatus(id, "completed");
    } catch (error) {
      console.error("Error accepting buy request:", error);
      alert("Failed to initiate payment.");
    }
  }
  
  // acceptBuyRequest: async (amount, productId, userId) => {
  //   try {
  //     // Send a POST request to create a payment intent with the necessary parameters
  //     const response = await axios.post(`${BASE_URL}/api/payment/create-payment-intent`, null, {
  //       params: { amount, productId, userId }
  //     });
      
  //     // Assuming the response contains a sessionId
  //     const { sessionId } = response.data;

  //     // Handle the sessionId, e.g., use it to redirect to a checkout page
  //     console.log("Payment initiation successful! Session ID:", sessionId);

  //     // Optionally update the status of the trade request after initiating payment
  //     // You may need to update this according to your application's logic
  //     // await TradeRequestApi.updateStatus(id, "completed");
  //   } catch (error) {
  //     console.error("Error accepting buy request:", error);
  //     alert("Failed to initiate payment.");
  //   }
  // }
};
