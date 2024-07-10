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
  }
};
