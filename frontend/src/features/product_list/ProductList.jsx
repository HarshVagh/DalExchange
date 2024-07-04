import React, { useEffect, useState } from "react";
import { ProductListingAPI } from "../../services/productListingApi";
import Header from "../../components/AppHeader";
import ProductCard from "./components/ProductCard";
import Sidebar from "./components/Sidebar";
import Pagination from "./components/Pagination";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [search, setSearch] = useState("");
  const [pageData, setPageData] = useState({
    page: 0,
    size: 12,
    totalElements: 12,
    totalPages: 1
  });

  const [filters, setFilters] = useState({
    minPrice: -1,
    maxPrice: -1,
    categories: [],
    conditions: []
  });
  
  const headerConfig = {
    search: true,
    requests: true,
    notifications: true,
    add: true,
    profile: true
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async (page = pageData.page) => {
    const params = {
      page: page,
      size: pageData.size
    }
    if(search && search.length > 0) {
      params["search"] = search;
    }
    if(filters.minPrice !== -1) {
      params["minPrice"] = filters.minPrice;
    }
    if(filters.maxPrice !== -1) {
      params["maxPrice"] = filters.maxPrice;
    } 
    if(filters.categories.length > 0) {
      params["categories"] = filters.categories;
    } 
    if(filters.conditions.length > 0) {
      params["conditions"] = filters.conditions;
    }
    const setters = {
      products: setProducts,
      isLoading: setIsLoading,
      error: setError,
      pageData: setPageData
    };
    console.log("request params: ", params)
    await ProductListingAPI.get(setters, params);
  };

  const handlePageChange = (newPage) => {
    fetchProducts(newPage);
  };

  const handleSearchSubmit = () => {
    fetchProducts();
  };

  const handleFilterSubmit = () => {
    fetchProducts();
  };

  const handleFilterUpdate = (updatedfilters) => {
    setFilters(updatedfilters);
  };

  return (
    !isLoading && <div className="flex flex-col h-screen">
      <Header 
        config={headerConfig} 
        search={search}
        setSearch={setSearch}
        onSearchSubmit={handleSearchSubmit} />
      <div className="lg:flex lg:flex-1 md:flex md:flex-1">
        
        <Sidebar 
          filters={filters}
          onFilterUpdate={handleFilterUpdate} 
          onFilterSubmit={handleFilterSubmit}>
        </Sidebar>
        
        {error && <div className="flex justify-center h-16 w-full" >
          <div className="flex items-center py-4 px-12 mt-4 text-sm text-red-600 rounded-lg bg-red-50 border-2 border-red-600" role="alert">
            <span className="sr-only">Error</span>
            <div>
              <span className="font-medium">Error!</span> {error.message}
            </div>
          </div>
        </div>}

        {!error && products && products.length === 0 && <div className="flex justify-center h-16 w-full" >
          <div className="p-3 px-12 mt-4 text-sm font-medium text-gray-800 rounded-lg bg-gray-50 border-2 border-gray-800" role="alert">
            Sorry there are no products available at the movement.
          </div>
        </div>}
        
        {products && <div className="flex flex-col">
          <div className="flex-grow flex-1 grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 p-6">
              {products.map((product) => (
                <ProductCard
                  key={product.productId}
                  product={product}
                ></ProductCard>
              ))}
          </div>

          {pageData.totalPages > 1 && <div className="flex justify-center mt-4 mb-4">
              <Pagination pageData={pageData} onPageChange={handlePageChange}></Pagination>
          </div>}
        </div>}        
      </div>
    </div>
  );
};

export default ProductList;
