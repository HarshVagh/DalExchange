import React, { useEffect, useState } from "react";
import { ProductListingAPI } from "../../services/productListingApi";
import Header from "../../components/header";
import ProductCard from "./components/ProductCard";
import Sidebar from "./components/Sidebar";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
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

  const fetchProducts = async () => {
    await ProductListingAPI.get(setProducts, setIsLoading, setError);
  };

  return (
    !isLoading && <div className="flex flex-col h-screen">
      <Header config={headerConfig} />
      <div className="lg:flex lg:flex-1 md:flex md:flex-1">
        
        <Sidebar></Sidebar>
        
        {error && <div className="flex justify-center h-16 w-full" >
          <div className="flex items-center py-4 px-12 mt-4 text-sm text-red-600 rounded-lg bg-red-50 border-2 border-red-600" role="alert">
            <svg className="flex-shrink-0 inline w-4 h-4 me-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
              <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
            </svg>
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
        </div>}
      </div>
    </div>
  );
};

export default ProductList;
