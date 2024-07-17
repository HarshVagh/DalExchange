import axios from "axios";

const BASE_URL = "http://localhost:8080";

const fetchSoldItems = async (productId) => {
  try {
    const response = await axios.get(`${BASE_URL}/profile/sold_products`, {
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
