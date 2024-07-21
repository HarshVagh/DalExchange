import axios from 'axios';

const BASE_URL = 'http://localhost:8080'; 

const ProductDetailsApi = {
  fetchProductDetails: async (productId) => {
    try {
      const response = await axios.get(`${BASE_URL}/product_details`, {
        params: {
          productId: productId
        }
      });
      return response.data;
    } catch (error) {
      throw new Error('Failed to fetch product details');
    }
  },

  addToFavorite: async (productId) => {
    try {
      const response = await axios.get(`${BASE_URL}/product_details/favorite`, {
        params: {
          productId: productId
        }
      });
      return response.data;
    } catch (error) {
      throw new Error('Failed to add to favorites');
    }
  },

  createTradeRequest: async (requestBody) => {
    try {
      const response = await axios.post(`${BASE_URL}/create_trade_request`, requestBody);
      return response.data; // Adjust as per API response format
    } catch (error) {
      throw new Error('Failed to create trade request');
    }
  }
  
};

export default ProductDetailsApi;
