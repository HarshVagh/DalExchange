import axios from "axios";

const fetchSavedItems = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8080/saved_products/${userId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const removeSavedItem = async (userId, productId) => {
  try {
    const response = await axios.post(`http://localhost:8080/${userId}/${productId}/removesaved`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const SavedItemsApi = {
  fetchSavedItems,
  removeSavedItem,
};

export default SavedItemsApi;
