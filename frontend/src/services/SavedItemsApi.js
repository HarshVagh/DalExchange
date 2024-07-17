import axios from "axios";

const BASE_URL = "http://localhost:8080";

const fetchSavedItems = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/profile/saved_products`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const removeSavedItem = async (productId) => {
  try {
    const response = await axios.post(`${BASE_URL}/profile/remove_saved/${productId}`);
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
