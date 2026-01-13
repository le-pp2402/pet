import type { Cart } from "../types/Cart";

export const CCartItem = ({ item }: { item: Cart }) => {

    return (
        <div className="flex flex-col gap-2 rounded-xl border border-slate-200 bg-white p-5 shadow-sm">
            <span className="text-xs uppercase tracking-wide text-slate-400">Product</span>
            <span className="text-lg font-semibold text-slate-900">#{item.id}</span>
            <div className="flex items-center justify-between text-sm text-slate-600">
                <span className="font-medium text-slate-700">Title</span>
                <span>{item.title}</span>
            </div>
            <div className="flex items-center justify-between text-sm text-slate-600">
                <span className="font-medium text-slate-700">Quantity</span>
                <span>{item.quantity}</span>
            </div>
        </div>
    );
};