import React from "react";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import DeleteIcon from "@mui/icons-material/Delete";

function CartItem({
    id,
    name,
    description,
    category,
    price,
    quantity,
    increaseQuantity,
    reduceQuantity,
    removeItem,
}) {
    function handleAdd() {
        increaseQuantity(id);
    }
    function handleRemove() {
        reduceQuantity(id);
    }
    function handleDelete() {
        removeItem(id);
    }

    return (
        <div className="flex justify-between w-full align-middle text-gray-900 bg-lime-50 p-4 rounded-2xl">
            <h3 className="text-xl w-full">{name}</h3>
            <div className="flex gap-4">
                <div className="flex gap-2 align-middle">
                    <button onClick={handleAdd}>
                        <AddIcon
                            sx={{
                                color: "#4a5565",
                                "&:hover": { color: "black" },
                            }}
                        />
                    </button>
                    <p className="text-xl text-gray-600 align-middle text-center">
                        {quantity}
                    </p>
                    <button onClick={handleRemove}>
                        <RemoveIcon
                            sx={{
                                color: "#4a5565",
                                "&:hover": { color: "black" },
                            }}
                        />
                    </button>
                </div>
                <h2 className="text-xl text-gray-600">${price}</h2>

                <button>
                    <DeleteIcon
                        onClick={handleDelete}
                        sx={{ color: "#4a5565", "&:hover": { color: "red" } }}
                    />
                </button>
            </div>
        </div>
    );
}

export default CartItem;
