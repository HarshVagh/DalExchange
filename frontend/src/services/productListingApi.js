import axios from "axios";

const BASE_URL = "http://localhost:8080";

export const ProductListingAPI = {
  get: async (setters, params) => {
    try {
      setters.isLoading(true);
      setters.error(null);
      const response = await axios.get(BASE_URL + "/products_listing", { 
          params: params,
          paramsSerializer: {indexes: null }
      });
      const { content, ...pageData } = response.data;
      setters.products(content);
      setters.pageData(pageData);
    } catch (error) {
      console.error("Error fetching products:", error);
      setters.error(error);
    } finally {
      setters.isLoading(false);
    }
  },
  getCategories: async (setters) => {
    try {
      const response = await axios.get(BASE_URL + "/product_categories");
      setters.categories(response.data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  }
};
