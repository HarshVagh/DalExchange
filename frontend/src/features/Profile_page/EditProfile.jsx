import React, { useState,useEffect } from "react";
import Header from "../../components/Header";
import axios from "axios";
import toast from 'react-hot-toast';
import SubHeader from "../../components/SubHeader";
import Loader from "../../components/Loader";
import ErrorAlert from "../../components/ErrorAlert";
import DataNotFound from "../../components/DataNotFound";

const EditProfile = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    phoneNo: "",
    fullName: "",
    profilePicture: "",
    bio: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const userid = 1;

  const [profileData, setProfileData] = useState([])
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
      setFormData(data)
      console.log(data, "profile details");
    
    } catch (error) {
      console.error("Failed to fetch product data", error);
       setError(error);
       setIsLoading(false);
    }finally {
      setIsLoading(false);
    }

  };
  const updateUserProfile = async (payload) => {
    const params = new URLSearchParams(window.location.search);
  
    const updatePromise = axios.put(`http://localhost:8080/edit_user/${userid}`, payload, {
      params: params,
      paramsSerializer: { indexes: null }
    });
  
    toast.promise(
      updatePromise,
      {
        loading: 'Updating profile...',
        success: 'Profile updated successfully!',
        error: 'Failed to update profile.'
      },
      {
        style: {
          minWidth: '250px',
        },
        success: {
          duration: 1000,
          icon: '👏',
        },
      }
    );
  
    try {
      const response = await updatePromise;
      const data = response.data;
      // setFormData(data);
      console.log(data, "profile details");
    } catch (error) {
      console.error("Failed to update profile", error);
    }
  };

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async(e) => {
    e.preventDefault();
    // Add your form submission logic here
    console.log(formData,"form submission");
   await updateUserProfile(formData);
  //  alert("profile updated")

  };

  return (
    <>
      <Header config={headerConfig} />
      <SubHeader title={'Back to Profile'} backPath={'/profile'} />
      {isLoading && <Loader title={'Loading Profile Details...'} />}
      {!isLoading && error && <ErrorAlert message={error.message} />}
      {!isLoading && !error && profileData && profileData.length > 0 ? (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center py-8 pt-14">
        <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
          <h2 className="text-2xl font-bold mb-6 text-center">Edit Profile</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label className="block text-gray-700 mb-2">Username</label>
              <input
                type="text"
                name="username"
                value={formData.username}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 mb-2">Password</label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 mb-2">Email</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 mb-2">Phone Number</label>
              <input
                type="tel"
                name="phoneNo"
                value={formData.phoneNo}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 mb-2">Full Name</label>
              <input
                type="text"
                name="fullName"
                value={formData.fullName}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            {/* <div className="mb-4">
              <label className="block text-gray-700 mb-2">
                Profile Picture
              </label>
              <input
                type="text"
                name="profilePicture"
                value={formData.profilePicture}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter URL"
              />
            </div> */}
            <div className="mb-4">
              <label className="block text-gray-700 mb-2">Bio</label>
              <textarea
                name="bio"
                value={formData.bio}
                onChange={handleChange}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <button
              type="submit"
              className="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition duration-200"
            >
              Save Changes
            </button>
          </form>
        </div>
      </div>
      ) : (
        <div className="my-20">
          <DataNotFound message={"Oops! No items sold yet."} />
        </div> )}
      {/* <Toaster/> */}

    </>
  );
};

export default EditProfile;
