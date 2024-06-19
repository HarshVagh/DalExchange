import React from "react";
import StoreIcon from "../assets/icons/store-solid.svg";
import SearchIcon from "../assets/icons/magnifying-glass-solid.svg";
import PlusIcon from "../assets/icons/plus-solid.svg";
import HandshakeIcon from "../assets/icons/handshake-solid.svg";
import BellIcon from "../assets/icons/bell-solid.svg";
import UserPlaceholder from "../assets/images/placeholder-user.jpg";

const header = (config) => {
	config = config.config;
  return (
    <header className="bg-gray-900 text-white py-4 px-6 flex justify-between items-center">
			<div className="flex justify-start items-center">
        <img alt="" className="h-8 w-8 mr-4" src={StoreIcon} />
				<h1 className="text-2xl font-bold">Dal Exchange</h1>
			</div>
			<div className="flex justify-end items-center gap-4">
        {config.search && <div className="relative w-96">
					<img alt="" className="w-6 h-6 absolute left-3 top-1/2 -translate-y-1/2 text-gray-100" src={SearchIcon} />
					<input
						className="bg-gray-800 rounded-md pl-12 pr-4 py-2 text-md focus:outline-none focus:ring-2 focus:ring-gray-600 w-full"
						placeholder="Search products..."
						type="search"
					/>
				</div>}
        {config.requests && <img alt="" className="w-8 h-8" src={HandshakeIcon} /> }
        {config.notifications && <img alt="" className="w-6 h-6" src={BellIcon} /> }
        {config.add && <button type="button" className="flex px-2.5 py-2.5 text-sm text-white font-medium bg-blue-700 hover:bg-blue-800 rounded-full dark:bg-blue-600 focus:outline-none">
          <img alt="" className="h-5 w-5" src={PlusIcon}/>
        </button>}
        {config.profile && <img alt="" className="w-10 h-10 rounded-full" src={UserPlaceholder} />}
			</div>
		</header>
  )
}

export default header;