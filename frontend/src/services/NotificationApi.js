import axios from "axios";

const BASE_URL = "http://localhost:8080";

export const NotificationApi = {
  get: async (setters) => {
    try {
      setters.isLoading(true);
      setters.error(null);
      const response = await axios.get(BASE_URL + "/notifications");
      setters.notifications(response.data);
    } catch (error) {
      console.error("Error fetching trade requests: ", error);
      setters.error(error);
    } finally {
      setters.isLoading(false);
    }
  },
  mark: async (id) => {
    try {
      await axios.put(BASE_URL+"/notifications/mark/"+id, null);
    } catch (error) {
      console.error("Error marking notifcation as read: ", error);
    }
  },
  create: async (body) => {
    try {
      const response = await axios.post(BASE_URL+"notifications/add", body);
      console.log("Notifcation created successfully:", response.data);
    } catch (error) {
      console.error("Error creating notifcation:", error);
    }
  }
};