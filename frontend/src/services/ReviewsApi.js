import axios from "axios";

const BASE_URL = "http://localhost:8080";

export const ReviewsApi = {
  fetchProductRatings: async (productId) => {
    try {
      const response = await axios.get(`${BASE_URL}/profile/product_ratings`, {
        params: { id: productId },
        paramsSerializer: { indexes: null },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  },
};
