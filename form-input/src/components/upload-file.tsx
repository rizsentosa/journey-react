import Input from "./input"

type Props = {
  previewUrl: string | null,
  onChange: (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>) => void,
  error?: string,
  fileName?: string
}

export default function UploadFile({ onChange, fileName, previewUrl, error }: Props) {
  return (
    <div className="flex flex-col gap-2 md:col-span-2">
      <label className="text-sm font-medium text-slate-900 tracking-wide">
        Foto Profil / Berkas
      </label>

      <div className="flex flex-col sm:flex-row items-start sm:items-center gap-4">
        <div className="w-28 h-28 rounded-2xl border-2 border-dashed border-slate-300 bg-slate-50 overflow-hidden flex items-center justify-center shrink-0 shadow-inner relative group">
          {previewUrl ? (
            <img src={previewUrl} alt="Preview" className="w-full h-full object-cover" />
          ) : (
            <div className="flex flex-col items-center justify-center p-2 text-center">
              <svg className="w-8 h-8 text-slate-400 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              <span className="text-[10px] text-slate-400 font-medium">No Image</span>
            </div>
          )}
        </div>
        <div className="flex flex-col gap-2 w-full">
          <Input
            label="Foto Profil / Berkas"
            type="file"
            name="image"
            accept="image/*"
            onChange={onChange}
            error={error}
            className={fileName ? "text-transparent" : ""}
            helperText="Format yang didukung: JPG, PNG, atau WEBP."
          />
          {fileName && (
            <div className="flex items-center gap-2 px-3 py-1.5 rounded-lg bg-slate-100 border border-slate-200 text-xs text-slate-700 w-fit">
              <svg className="w-4 h-4 text-emerald-600 shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7" />
              </svg>
              <span className="font-medium truncate max-w-[240px]" title={fileName}>
                {fileName}
              </span>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}