import React from "react";
import Header from "../../components/header";

const ProductList = () => {
  return (
    <div className="flex flex-col">
      <Header/>
      <div className="flex flex-1">
        <aside className="bg-gray-100 p-6 border-r w-64">
          <h2 className="text-lg font-medium mb-4">Filters</h2>
          <div className="space-y-4">
            <div>
              <h3 className="text-base font-medium mb-2">Price</h3>
              <div className="flex items-center gap-2">
                <input
                  className="bg-white rounded-md px-3 py-2 text-sm w-24 focus:outline-none focus:ring-2 focus:ring-gray-600"
                  placeholder="Min"
                  type="number"
                />
                <span>-</span>
                <input
                  className="bg-white rounded-md px-3 py-2 text-sm w-24 focus:outline-none focus:ring-2 focus:ring-gray-600"
                  placeholder="Max"
                  type="number"
                />
              </div>
            </div>
            <div>
              <h3 className="text-base font-medium mb-2">Category</h3>
              <div className="space-y-2">
                <div className="flex items-center gap-2">
                  <checkbox id="category-furniture" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="category-furniture"
                  >
                    Furniture
                  </label>
                </div>
                <div className="flex items-center gap-2">
                  <checkbox id="category-decor" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="category-decor"
                  >
                    Decor
                  </label>
                </div>
                <div className="flex items-center gap-2">
                  <checkbox id="category-clothing" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="category-clothing"
                  >
                    Clothing
                  </label>
                </div>
                <div className="flex items-center gap-2">
                  <checkbox id="category-accessories" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="category-accessories"
                  >
                    Accessories
                  </label>
                </div>
                <div className="flex items-center gap-2">
                  <checkbox id="category-electronics" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="category-electronics"
                  >
                    Electronics
                  </label>
                </div>
                <div className="flex items-center gap-2">
                  <checkbox id="category-books" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="category-books"
                  >
                    Books
                  </label>
                </div>
              </div>
            </div>
            <div>
              <h3 className="text-base font-medium mb-2">Condition</h3>
              <div className="space-y-2">
                <div className="flex items-center gap-2">
                  <checkbox id="condition-new" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="condition-new"
                  >
                    New
                  </label>
                </div>
                <div className="flex items-center gap-2">
                  <checkbox id="condition-used" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="condition-used"
                  >
                    Used
                  </label>
                </div>
                <div className="flex items-center gap-2">
                  <checkbox id="condition-vintage" />
                  <label
                    className="text-sm font-medium"
                    htmlFor="condition-vintage"
                  >
                    Vintage
                  </label>
                </div>
              </div>
            </div>
          </div>
        </aside>
        <main className="flex-1 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-6">
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="
							/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">Vintage Typewriter</h3>
              <p className="text-gray-600 text-sm">$49.99</p>
            </div>
          </div>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">Retro Camera</h3>
              <p className="text-gray-600 text-sm">$79.99</p>
            </div>
          </div>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">Vintage Suitcase</h3>
              <p className="text-gray-600 text-sm">$39.99</p>
            </div>
          </div>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">Antique Lamp</h3>
              <p className="text-gray-600 text-sm">$59.99</p>
            </div>
          </div>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">
                Vintage Record Player
              </h3>
              <p className="text-gray-600 text-sm">$99.99</p>
            </div>
          </div>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">Antique Vase</h3>
              <p className="text-gray-600 text-sm">$29.99</p>
            </div>
          </div>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">Vintage Typewriter</h3>
              <p className="text-gray-600 text-sm">$49.99</p>
            </div>
          </div>
          <div className="bg-white rounded-lg shadow-md overflow-hidden">
            <img
              alt="Product Image"
              className="w-full h-48 object-cover"
              height={300}
              src="/placeholder.png"
              style={{
                aspectRatio: "400/300",
                objectFit: "cover",
              }}
              width={400}
            />
            <div className="p-4">
              <h3 className="text-lg font-medium mb-2">Retro Camera</h3>
              <p className="text-gray-600 text-sm">$79.99</p>
            </div>
          </div>
        </main>
      </div>
      {/* <div className="flex justify-center mt-6 mb-8"></div>  */}
    </div>
  );
};

export default ProductList;
