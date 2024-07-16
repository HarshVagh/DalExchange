import axios from "axios";

const PurchasedHistoryApi = async (userId, productId) => {
  try {
    const response = await axios.get(`http://localhost:8080/purchased_products/${userId}`, {
      params: { id: productId },
      paramsSerializer: { indexes: null },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default PurchasedHistoryApi;
