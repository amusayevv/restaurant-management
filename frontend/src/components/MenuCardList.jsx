import React, { useState, useEffect } from "react";
import MenuCard from "./MenuCard";

function MenuCardList({ addToCart }) {
    const [menuItems, setMenuItems] = useState([]);

    const fetchData = async () => {
        const url = "http://localhost:8080/menu";

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`Response status: ${response.status}`);
            }

            const jsonData = await response.json();
            const dataArray = Object.values(jsonData);
            console.log(dataArray);
            setMenuItems(dataArray);
        } catch (error) {
            console.log(error.message);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <li className="grid pt-10 grid-cols-2 gap-4 w-full">
            {menuItems.map((menuItem) => {
                return (
                    <MenuCard
                        key={menuItem.id}
                        id={menuItem.id}
                        name={menuItem.name}
                        description={menuItem.description}
                        category={menuItem.category}
                        price={menuItem.price}
                        addToCart={addToCart}
                    ></MenuCard>
                );
            })}
        </li>
    );
}

export default MenuCardList;
