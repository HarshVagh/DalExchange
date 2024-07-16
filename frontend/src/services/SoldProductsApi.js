import axios from "axios";

const fetchSoldItems = async (userId, productId) => {
  try {
    const response = await axios.get(`http://localhost:8080/sold_products/${userId}`, {
      params: { id: productId },
      paramsSerializer: { indexes: null },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const SoldProductsApi = {
  fetchSoldItems,
};

export default SoldProductsApi;
