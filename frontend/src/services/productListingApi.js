import axios from "axios";

const BASE_URL = "http://localhost:8080";

export const ProductListingAPI = {
  get: async (setProducts, setIsLoading, setError) => {
    try {
      setIsLoading(true);
      setError(null);
      const response = await axios.get(BASE_URL + "/products_listing");
      setProducts(response.data);
    } catch (error) {
      console.error("Error fetching products:", error);
      setError(error);
    } finally {
      setIsLoading(false);
    }
  },
};
