import React from 'react'
import Header from "../../components/header";

const ProductDetails = () => {
  return (
    <div>
      <Header/>
      <div className="flex flex-1">
        <main className="flex-1 p-6">
          <div className="grid grid-cols-1 md:grid-cols-10 gap-8">
            <div className="md:col-span-3">
              <img
                alt="Product Image"
                className="w-full rounded-lg"
                height={600}
                src="/placeholder.png"
                style={{
                  aspectRatio: "1/1",
                  objectFit: "cover",
                }}
                width={600}
              />
              <div className="grid grid-cols-4 gap-4 mt-4">
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt="Thumbnail 1"
                    className="w-full aspect-square object-cover"
                    height={100}
                    src="/placeholder.png"
                    width={100}
                  />
                </button>
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt="Thumbnail 2"
                    className="w-full aspect-square object-cover"
                    height={100}
                    src="/placeholder.png"
                    width={100}
                  />
                </button>
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt="Thumbnail 3"
                    className="w-full aspect-square object-cover"
                    height={100}
                    src="/placeholder.png"
                    width={100}
                  />
                </button>
                <button className="border rounded-lg overflow-hidden">
                  <img
                    alt="Thumbnail 4"
                    className="w-full aspect-square object-cover"
                    height={100}
                    src="/placeholder.png"
                    width={100}
                  />
                </button>
              </div>
            </div>
            <div className="md:col-span-7">
              <h1 className="text-3xl font-bold mb-5 mt-3">Vintage Typewriter</h1>
              <p className="text-gray-600 mb-6">
                This vintage typewriter is a true piece of history. With its
                classic design and sturdy construction, it's a timeless addition
                to any office or home decor. Fully refurbished and in excellent
                working condition, this typewriter is a must-have for any
                vintage enthusiast.
              </p>
              
              <div className="flex items-center justify-between mb-6">
                <h2 className="text-2xl font-bold">$49.99</h2>
                <div className="flex items-center gap-4 mr-12">
                  <button size="sm" variant="outline" 
										className="bg-transparent hover:bg-neutral-900 text-700 font-semibold hover:text-white py-2 px-4 border border-500 hover:border-transparent rounded">
                    Add to Cart
                  </button>
                  <button size="sm" variant="outline" className="flex flex-end gap-2 bg-transparent hover:bg-neutral-900 text-700 font-semibold hover:text-white py-2 px-4 border border-500 hover:border-transparent rounded">
                    <HeartIcon />
                    Favorite
                  </button>
                </div>
              </div>
              <div className="grid grid-cols-5 gap-4">
                <div>
                  <h3 className="text-base font-medium mb-2">Condition</h3>
                  <p className="text-gray-600">Used, Excellent</p>
                </div>
                <div>
                  <h3 className="text-base font-medium mb-2">Status</h3>
                  <p className="text-gray-600">Available</p>
                </div>
                <div>
                  <h3 className="text-base font-medium mb-2">Shipping</h3>
                  <p className="text-gray-600">Free Shipping</p>
                </div>
                <div>
                  <h3 className="text-base font-medium mb-2">Category</h3>
                  <p className="text-gray-600">Furniture</p>
                </div>
                <div>
                  <h3 className="text-base font-medium mb-2">Use Duration</h3>
                  <p className="text-gray-600">5 year</p>
                </div>
              </div>
              <div className="grid grid-cols-2 gap-4 mt-10">
                <div>
                  <h3 className="text-base font-bold font-medium mb-2">Seller</h3>
                  <div className="flex items-center gap-2">
                    <div>
                      <p className="font-medium">Vintage Emporium</p>
                      <p className="text-gray-600 text-sm mt-2">Seller since 2015</p>
                      <div className="flex items-center gap-1 text-xs font-semibold">
                        <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-primary" />
                        <StarIcon className="w-3.5 h-5 fill-muted" />
                        <span className="text-gray-500  dark:text-gray-400">
                          (745)
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  )
}

 const HeartIcon = (props) => {
    return (
      <svg
        {...props}
        xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      >
        <path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z" />
      </svg>
    );
  }
  
  const StarIcon = (props) => {
    return (
      <svg
        {...props}
        xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      >
        <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
      </svg>
    );
  }
  

export default ProductDetails
