type Props = {
  title: string,
  message: string,
  onConfirm?: () => void
}
export default function Error({ title, message, onConfirm }: Props) {
  return (
    <div className="bg-red-300 p-5 flex justify-center ">
      <h2 className="text-xl text-red-600 font-bold ">{title}</h2>
      <p className="mt-1 text-sm text-red-600 mb-4">{message}</p>
      {onConfirm && (
        <div className="flex justify-end">
          <button onClick={onConfirm} className="rounded-lg bg-red-600 px-4 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-700 active:scale-95 transition-all focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-offset-2">okay</button>
        </div>
      )}
    </div>
  )
}