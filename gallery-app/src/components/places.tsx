import type { TAvailablePlace } from "../types/gallery.type"

type TProps = {
  title: string,
  places: TAvailablePlace[],
  fallbackText?: string,
  onSelectPlace: (id: string) => void;
}
export default function Places({ title, places, fallbackText, onSelectPlace }: TProps) {
  return (
    <section className="max-w-[85rem] mt-8 mx-auto p-4 border-2 border-solid border-blue-600">
      <h2 className="mb-4 text-sky-500 text-xl">{title}</h2>
      {places.length === 0 && <p className="text-center text-sky-500">{fallbackText}</p>}
      {places.length > 0 && (
        <ul className="mx-auto my-8 max-w-[80rem] grid grid-cols-[repeat(auto-fill,minmax(18rem,1fr))] gap-8 p-0 list-none">
          {places.map(place => (
            <li key={place.id} className="group relative flex flex-col overflow-hidden rounded-lg bg-[#1f1c2c] shadow-md transition-all duration-300 hover:shadow-xl hover:-translate-y-1 animate-slide-up">
              <button onClick={() => onSelectPlace(place.id)} className="flex h-full w-full flex-col text-left focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-[#1f1c2c]">
                <div className="h-48 w-full overflow-hidden bg-gray-800">
                  <img src={place.image.src} alt={place.image.alt} className="h-full w-full object-cover transition-transform duration-300 group-hover:scale-105" />
                </div>
                <div className="p-4">
                  <h3 className="text-lg font-semibold text-white group-hover:text-blue-400 transition-colors">{place.title}</h3>
                </div>
              </button>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}