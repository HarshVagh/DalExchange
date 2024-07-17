import axios from "axios";

const BASE_URL = "http://localhost:8080";

const PurchasedHistoryApi = async (productId) => {
  try {
    const response = await axios.get(`${BASE_URL}/profile/purchased_products`, {
      params: { id: productId },
      paramsSerializer: { indexes: null },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default PurchasedHistoryApi;
