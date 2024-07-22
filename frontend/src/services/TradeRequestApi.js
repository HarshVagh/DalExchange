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
  }
};
