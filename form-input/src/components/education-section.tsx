import Input from "./input";
import SelectOption from "./select-option";

const degreeSchoolOptions = [
  { label: '-- Pilih Jenjang --', value: '' },
  { label: 'SMK', value: 'SMK' },
  { label: 'SMA', value: 'SMA' },
  { label: 'D3', value: 'D3' },
  { label: 'S1', value: 'S1' },
  { label: 'S2', value: 'S2' },
  { label: 'S3', value: 'S3' },
];

export type EducationItem = {
  id: string;
  schoolName: string;
  degree: string;
  major: string;
  startDateEducation: string;
  endDateEducation: string;
};

interface EducationSectionProps {
  educations: EducationItem[];
  errors: Record<string, string>;
  onAdd: () => void;
  onRemove: (id: string) => void;
  onChange: (index: number, field: keyof EducationItem, value: string) => void;
}

export default function EducationSection({
  educations,
  errors,
  onAdd,
  onRemove,
  onChange,
}: EducationSectionProps) {
  return (
    <div className="flex flex-col gap-6">
      {educations.map((item, index) => (
        <div key={item.id} className="border border-slate-100 p-4 rounded-2xl">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-slate-100 text-xl font-bold">
              Education {index + 1}
            </h2>
            {educations.length > 1 && (
              <button
                type="button"
                onClick={() => onRemove(item.id)}
                className="text-red-400 hover:text-red-300 text-sm font-medium cursor-pointer"
              >
                Delete
              </button>
            )}
          </div>

          <div className="grid grid-cols-1 gap-6 mt-4">
            <Input
              label="Nama Sekolah / Universitas"
              type="text"
              name={`educations[${index}].schoolName`}
              value={item.schoolName}
              onChange={(e) => onChange(index, 'schoolName', e.target.value)}
              placeholder="nama instansi pendidikan"
              error={errors[`educations.${index}.schoolName`] || ''}
            />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-4">
            <SelectOption
              label="Jenjang"
              name={`educations[${index}].degree`}
              defaultValue={item.degree}
              onChange={(e: any) => onChange(index, 'degree', e.target.value)}
              options={degreeSchoolOptions}
              error={errors[`educations.${index}.degree`] || ''}
            />
            <Input
              label="Jurusan"
              type="text"
              name={`educations[${index}].major`}
              value={item.major}
              onChange={(e) => onChange(index, 'major', e.target.value)}
              placeholder="jurusan pendidikan"
              error={errors[`educations.${index}.major`] || ''}
            />
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-4">
            <Input
              label="Start Date"
              type="date"
              name={`educations[${index}].startDateEducation`}
              value={item.startDateEducation}
              onChange={(e) => onChange(index, 'startDateEducation', e.target.value)}
              error={errors[`educations.${index}.startDateEducation`] || ''}
            />
            <Input
              label="End Date"
              type="date"
              name={`educations[${index}].endDateEducation`}
              value={item.endDateEducation}
              onChange={(e) => onChange(index, 'endDateEducation', e.target.value)}
              error={errors[`educations.${index}.endDateEducation`] || ''}
            />
          </div>
        </div>
      ))}

      <button
        type="button"
        onClick={onAdd}
        className="text-cyan-400 hover:underline mt-2 font-medium w-fit cursor-pointer"
      >
        + Add More Education
      </button>
    </div>
  );
}