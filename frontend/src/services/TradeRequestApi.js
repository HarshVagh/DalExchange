import AxiosInstance from "./AxiosInstance";
//import axios from 'axios';
//import { loadStripe } from "@stripe/stripe-js";

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
  createPaymentIntent: async (productId) => {
    try {
      // Make a request to your backend to create a payment intent
      const response = await AxiosInstance.post("/api/payment/create-payment-intent", { productId});

      // Extract the session ID from the response
      const { sessionId } = response.data;

      // Redirect the user to Stripe Checkout
      if (window.Stripe) {
        window.Stripe('pk_test_51Pdgl8RuVIC7U6kcnDPmtuBFtSoX83Of4rWP09F9M6LkmxUPVCRgi0eRY5aAMXsxBOhCtlVpnF6JfUSRpF7NdHAh00DKHrJPs6').redirectToCheckout({ sessionId });
      } else {
        console.error('Stripe is not loaded');
      }
    } catch (error) {
      console.error('Error creating payment intent:', error);
    }
  },
  saveShippingAddress: async (body) => {
    try {
      const response = await AxiosInstance.post("api/payment/shippingaddress",body);
      console.log("Trade request created successfully:", response.data);
    } catch (error) {
      console.error("Error creating trade request:", error);
    }
  },
};
