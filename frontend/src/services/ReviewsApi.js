import axios from "axios";

const fetchProductRatings = async (userId, productId) => {
  try {
    const response = await axios.get(`http://localhost:8080/product_ratings/${userId}`, {
      params: { id: productId },
      paramsSerializer: { indexes: null },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default { fetchProductRatings };
