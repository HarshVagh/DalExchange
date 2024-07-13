import React,{useState, useEffect} from "react";
import Header from "../../components/AppHeader";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import DataNotFound from "../../components/DataNotFound";
import Loader from "../../components/Loader"




export default function Profile() {
  const navigate = useNavigate();
  const userid = 1;

  const [profileData, setProfileData] = useState([])
  const [loading, setLoading] = useState(false); // Add loading state


  const fetchUserProfile = async () => {
    try {
      const params = new URLSearchParams(window.location.search);
      const productId = params.get("id");
      console.log({ productId });
      
      const response = await axios.get(`http://localhost:8080/${userid}/profiledetails`, { 
        params: params,
        paramsSerializer: {indexes: null }
      });
      const data = response.data;
      setProfileData(data)
      console.log(data, "profile details");
    
    } catch (error) {
      console.error("Failed to fetch product data", error);
      // setError(error);
      // setLoading(false);
    }
    finally {
      setTimeout(()=>{
       setLoading(false)
      },800)
     ; // Set loading to false after fetching
   }
  };

  useEffect(() => {
    fetchUserProfile();
  }, []);


  return (
    <>
      <div className="bg-gray-100 dark:bg-gray-950 pt-4 pb-4 h-screen max-h-100">
      <Header />
        <div className="container mx-auto px-4 md:px-6 overflow-hidden py-">
          {/* <div className="grid md:grid-cols-[200px_1fr] gap-6"></div> */}
          <div className="flex flex-col items-center gap-4 dark:text-gray-400">
            <div className="pt-6">
              <img
                className="w-20 h-20 rounded-full"
                alt="User Avatar"
                src="https://img.freepik.com/premium-vector/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-vector-illustration_561158-3485.jpg"
              />
            </div>
            <div className="text-center">
              <h2 className="text-xl font-bold">{profileData?.username
              }</h2>
              <p className="text-gray-500 dark:text-gray-400 text-sm pt-4">
								{profileData?.bio}
							</p>
							{/* <button type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center my-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
								$250.00 wallet balance
							</button>  */}
              <br />
              <button
                type="button"
                class="text-white bg-black border border-gray-300 focus:outline-none hover:bg-white hover:text-black focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 my-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
                onClick={() => {
                  navigate("edit-profile");
                }}
              >
                Edit Profile
              </button>
            </div>
          </div>
          <div className="space-y-8">
            <div>
              {/* <div className="flex items-center justify-between mb-4 bg-gray-200">
								<h3 className="text-lg font-semibold dark:text-white">Saved Items</h3>
								<button className="dark:text-white" size="sm" variant="link">
									View More
								</button>
							</div> */}
              <div className=" grid gap-10 grid-cols-2 grid-rows-2 mx-50 content-center py-8">
                <div
                  className="flex justify-end"
                  onClick={() => {
                    navigate("saved-items");
                  }}
                >
                  <div className="bg-gray-200 text-center p-5 rounded-md w-80 cursor-pointer">
                    <p className="text-base">Saved Items</p>
                  </div>
                </div>
                <div
                  className="flex justify-start"
                  onClick={() => {
                    navigate("purchase-history");
                  }}
                >
                  <div className="bg-gray-200 text-center p-5 rounded-md w-80 cursor-pointer">
                    <p className="text-base">Purchase History</p>
                  </div>
                </div>
                <div
                  className="flex justify-end"
                  onClick={() => {
                    navigate("sold-items");
                  }}
                >
                  <div className="bg-gray-200 text-center p-5 rounded-md w-80 cursor-pointer">
                    <p className="text-base">Sold Items</p>
                  </div>
                </div>
                <div className="flex justify-start"
				
				onClick={() => {
                    navigate("reviews");
                  }}>
                  <div className="bg-gray-200 text-center p-5 rounded-md w-80 cursor-pointer">
                    <p className="text-base">Reviews</p>
                  </div>
                </div>
              </div>
            </div>

            
            {/* <Reviews /> */}
           
          </div>
        </div>
      </div>
    </>
  );
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
};

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
};
