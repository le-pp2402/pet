import { createAsyncThunk, createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { Cart } from "../../types/Cart";

const initialState = [] as Cart[];

const cartSlice = createSlice({
    name: 'cart',
    initialState: initialState,
    reducers: {
        addToCart(state, action: PayloadAction<number>) {
            const productId = action.payload;
            const existed = state.find(item => item.id == action.payload);
            if (existed) {
                existed.quantity += 1;
            } else {
                state.push({ id: productId, quantity: 1 });
            }
        },
    },
    extraReducers: (builder) => {
        builder.addCase(fetchProductId.fulfilled, (state, action) => {
            // action.payload chính là cục data sản phẩm lấy từ API về
            // Bạn hãy viết logic thêm sản phẩm vào state tại đây
            const product = action.payload;
            const existed = state.find(item => item.id == product.id);
            if (existed) {
                existed.quantity += 1;
                existed.image = product.image;
                existed.title = product.title;
            } else {
                state.push({
                    id: product.id,
                    quantity: 1,
                    title: product.title,
                    image: product.image
                });
            }
        });
    }
});

export const { addToCart } = cartSlice.actions;
export default cartSlice.reducer;

export const fetchProductId = createAsyncThunk(
    'cart/fetchProductId',
    async (productId: number) => {
        let response = await fetch(`https://fakestoreapi.com/products/${productId}`);
        let data = await response.json();
        return data;
    }
);