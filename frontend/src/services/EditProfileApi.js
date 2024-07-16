// services/EditProfileApi.js

import axios from "axios";

const BASE_URL = "http://localhost:8080"; // Update with your API base URL

const EditProfileApi = {
  fetchUserProfile: async (userId) => {
    try {
      const response = await axios.get(`${BASE_URL}/${userId}/profiledetails`);
      return response.data;
    } catch (error) {
      throw new Error("Failed to fetch user profile");
    }
  },

  updateUserProfile: async (userId, payload) => {
    try {
      const response = await axios.put(`${BASE_URL}/edit_user/${userId}`, payload);
      return response.data;
    } catch (error) {
      throw new Error("Failed to update user profile");
    }
  },
};

export default EditProfileApi;
