import React, { useEffect, useState, useCallback } from "react";
import { ProductListingAPI } from "../../services/productListingApi";
import Header from "../../components/Header";
import ProductCard from "./components/ProductCard";
import Sidebar from "./components/Sidebar";
import Pagination from "./components/Pagination";
import Loader from "../../components/Loader";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  
  const [search, setSearch] = useState("");
  const [tempSearch, setTempSearch] = useState(search);
  
  const [pageData, setPageData] = useState({
    page: 0,
    size: 12,
    totalElements: 12,
    totalPages: 1
  });
  const [tempPageData, setTempPageData] = useState(pageData);

  const [filters, setFilters] = useState({
    minPrice: -1,
    maxPrice: -1,
    categories: [],
    conditions: []
  });
  const [tempFilters, setTempFilters] = useState(filters);

  const headerConfig = {
    search: true,
    requests: true,
    notifications: true,
    add: true,
    profile: true
  };

  const fetchProducts = useCallback(async (page = pageData.page) => {
    const params = {
      page: page,
      size: pageData.size
    };
    if (search && search.length > 0) {
      params["search"] = search;
    }
    if (filters.minPrice !== -1) {
      params["minPrice"] = filters.minPrice;
    }
    if (filters.maxPrice !== -1) {
      params["maxPrice"] = filters.maxPrice;
    }
    if (filters.categories.length > 0) {
      params["categories"] = filters.categories;
    }
    if (filters.conditions.length > 0) {
      params["conditions"] = filters.conditions;
    }
    const setters = {
      products: setProducts,
      isLoading: setIsLoading,
      error: setError,
      pageData: setPageData
    };
    console.log("request params: ", params);
    await ProductListingAPI.get(setters, params);
  }, [filters, pageData.page, pageData.size, search]);

  useEffect(() => {
    fetchProducts();
  }, [fetchProducts]);

  const handlePageChange = (newPage) => {
    setTempPageData((prev) => ({ ...prev, page: newPage }));
    fetchProducts(tempPageData.page);
  };

  const handlePageSubmit = () => {
    setPageData(tempPageData);
    fetchProducts(tempPageData.page);
  };

  const handleSearchUpdate = (newSearch) => {
    setTempSearch(newSearch);
  };

  const handleSearchSubmit = () => {
    setSearch(tempSearch);
    fetchProducts();
  };

  const handleFilterSubmit = () => {
    setFilters(tempFilters);
    fetchProducts();
  };

  const handleFilterUpdate = (updatedFilters) => {
    setTempFilters(updatedFilters);
  };

  return (
    <div className="flex flex-col h-screen">
      <Header 
        config={headerConfig} 
        search={tempSearch}
        setSearch={handleSearchUpdate}
        onSearchSubmit={handleSearchSubmit} />
      <div className="lg:flex lg:flex-1 md:flex md:flex-1">
        
        <Sidebar 
          filters={tempFilters}
          onFilterUpdate={handleFilterUpdate} 
          onFilterSubmit={handleFilterSubmit}>
        </Sidebar>

        {isLoading && <Loader title={'Loading Trade Requests...'} />}
        
        {!isLoading && error && <div className="flex justify-center h-16 w-full" >
          <div className="flex items-center py-4 px-12 mt-4 text-sm text-red-600 rounded-lg bg-red-50 border-2 border-red-600" role="alert">
            <span className="sr-only">Error</span>
            <div>
              <span className="font-medium">Error!</span> {error.message}
            </div>
          </div>
        </div>}

        {!isLoading && !error && products && products.length === 0 && <div className="flex justify-center h-16 w-full" >
          <div className="p-3 px-12 mt-4 text-sm font-medium text-gray-800 rounded-lg bg-gray-50 border-2 border-gray-800" role="alert">
            Sorry there are no products available at the moment.
          </div>
        </div>}
        
        {!isLoading && !error && products && products.length > 0 && <div className="flex flex-col">
          <div className="flex-grow flex-1 grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 p-6">
              {products.map((product) => (
                <ProductCard
                  key={product.productId}
                  product={product}
                ></ProductCard>
              ))}
          </div>

          {pageData.totalPages > 1 && <div className="flex justify-center mt-4 mb-4">
              <Pagination pageData={tempPageData} onPageChange={handlePageChange} onPageSubmit={handlePageSubmit}></Pagination>
          </div>}
        </div>}        
      </div>
    </div>
  );
};

export default ProductList;
