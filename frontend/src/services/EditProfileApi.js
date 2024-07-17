import axios from "axios";

const BASE_URL = "http://localhost:8080";

const EditProfileApi = {
  fetchUserProfile: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/profile`);
      return response.data;
    } catch (error) {
      throw new Error("Failed to fetch user profile");
    }
  },

  updateUserProfile: async (payload) => {
    try {
      const response = await axios.put(`${BASE_URL}/profile/edit_user`, payload, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      return response.data;
    } catch (error) {
      throw new Error("Failed to update user profile");
    }
  },
};

export default EditProfileApi;