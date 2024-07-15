import axios from "axios";

const BASE_URL = "http://localhost:8080";

export const TradeRequestApi = {
  getTradeRequests: async (setters) => {
    try {
      setters.isLoading(true);
      setters.error(null);
      const sellResponse = await axios.get(BASE_URL + "/sell_requests");
      const buyResponse = await axios.get(BASE_URL + "/buy_requests");
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
      await axios.put(BASE_URL+"/update_trade_status/"+id, null, {
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
      const response = await axios.post(BASE_URL+"/create_trade_request", body);
      console.log("Trade request created successfully:", response.data);
    } catch (error) {
      console.error("Error creating trade request:", error);
    }
  }
};
