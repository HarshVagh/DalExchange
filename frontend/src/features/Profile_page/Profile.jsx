import React,{useState, useEffect} from "react";
import Header from "../../components/Header";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import SubHeader from "../../components/SubHeader";
import Loader from "../../components/Loader";
import ErrorAlert from "../../components/ErrorAlert";
import DataNotFound from "../../components/DataNotFound";

export default function Profile() {
  const navigate = useNavigate();
  const userid = 1;
  
  const [profileData, setProfileData] = useState([])
  const [isLoading, setIsLoading] = useState(false); 
  const [error, setError] = useState(null);

  const headerConfig = {
    search: false,
    requests: true,
    notifications: true,
    add: true,
    profile: true
  };

  const fetchUserProfile = async () => {
    try {
      setIsLoading(true);
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
       setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchUserProfile();
  }, []);


  return (
    <>
      <div className="bg-gray-100 dark:bg-gray-950 pt-4 pb-4 h-screen max-h-100">
      <Header config={headerConfig} />
      <SubHeader title={'User Profile'} backPath={'/products'} />
      {isLoading && <Loader title={'Loading Profile Details...'} />}
      {!isLoading && error && <ErrorAlert message={error.message} />}
      {!isLoading && !error && profileData && profileData.length > 0 ? (
        <div className="container mx-auto px-4 md:px-6 overflow-hidden py-">
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
      ) : (
        <div className="my-20">
          <DataNotFound message={"Oops! No items sold yet."} />
        </div> )}
      </div>
    </>
  );
}
