import React, { useState, useEffect } from "react";

function MenuCard({ id, name, description, category, price, addToCart }) {
    const [cart, setCart] = useState([]);

    function handleAddToCart() {
        addToCart({ id, name, description, category, price });
    }

    return (
        <div className="flex flex-col w-full p-10 box-border gap-6 bg-lime-50 rounded-2xl transition-all duration-150 hover:rounded-[64px]">
            <h3 className="text-3xl font-serif">{name}</h3>
            <p>{description}</p>
            <p className="w-fit px-2 py-1 bg-green-100 text-black rounded-full">
                {category
                    .toLowerCase()
                    .replace("_", " ")
                    .split(" ")
                    .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
                    .join(" ")}
            </p>
            <button
                onClick={handleAddToCart}
                className="w-full bg-green-800 text-white h-10 rounded-full hover:bg-green-700 active:bg-green-900"
            >
                Add to cart ${price}
            </button>
        </div>
    );
}
export default MenuCard;
