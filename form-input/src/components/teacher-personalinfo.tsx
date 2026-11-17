import Input from "./input";
import SelectOption from "./select-option";
import UploadFile from "./upload-file";

const genderOptions = [
  { label: '-- Pilih Gender --', value: '' },
  { label: 'MALE', value: 'male' },
  { label: 'FEMALE', value: 'female' },
];

const religionOptions = [
  { label: '-- Pilih Agama --', value: '' },
  { label: 'Islam', value: 'islam' },
  { label: 'Kristen Protestan', value: 'kristen_protestan' },
  { label: 'Katolik', value: 'katolik' },
  { label: 'Hindu', value: 'hindu' },
  { label: 'Buddha', value: 'buddha' },
  { label: 'Konghucu', value: 'konghucu' },
  { label: 'Lainnya', value: 'lainnya' },
];

interface TeacherPersonalInfoProps {
  values: Record<string, any>;
  errors: Record<string, string>;
  previewUrl: string | null;
  selectedFileName?: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function TeacherPersonalInfo({
  values,
  errors,
  previewUrl,
  selectedFileName,
  onChange,
}: TeacherPersonalInfoProps) {
  return (
    <div className="border border-slate-100 p-4 rounded-2xl">
      <h2 className="text-slate-100 text-xl font-bold mb-4">Form Teacher</h2>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Input
          label="Full Name"
          type="text"
          name="fullname"
          defaultValue={values?.fullname || ''}
          onChange={onChange}
          placeholder="please input your full name"
          error={errors.fullname}
        />
        <Input
          label="Nuptk"
          type="text"
          name="nuptk"
          defaultValue={values?.nuptk || ''}
          onChange={onChange}
          placeholder="please input your nuptk"
          error={errors.nuptk}
        />
        <Input
          label="Nip"
          type="text"
          name="nip"
          defaultValue={values?.nip || ''}
          onChange={onChange}
          placeholder="please input your nip"
          error={errors.nip}
        />
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-4">
        <Input
          label="NIK"
          type="text"
          name="nik"
          defaultValue={values?.nik || ''}
          onChange={onChange}
          placeholder="please input your nik"
          error={errors.nik}
          helperText="nomor induk keluarga"
        />
        <Input
          label="Tempat Lahir"
          type="text"
          name="placeOfBirth"
          defaultValue={values?.placeOfBirth || ''}
          onChange={onChange}
          placeholder="please input your place of birth"
          error={errors.placeOfBirth}
        />
        <Input
          label="Tanggal Lahir"
          type="date"
          name="dateBirth"
          defaultValue={values?.dateBirth || ''}
          onChange={onChange}
          error={errors.dateBirth}
        />
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-4">
        <Input
          label="Phone Number"
          type="text"
          name="phone"
          defaultValue={values?.phone || ''}
          onChange={onChange}
          placeholder="08123456789"
          error={errors.phone}
        />
        <SelectOption
          label="Gender"
          name="gender"
          defaultValue={values?.gender || ''}
          options={genderOptions}
          error={errors.gender}
        />
        <SelectOption
          label="Religion"
          name="religion"
          defaultValue={values?.religion || ''}
          options={religionOptions}
          error={errors.religion}
        />
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 items-center mt-4">
        <Input
          label="Alamat Email"
          type="email"
          name="email"
          defaultValue={values?.email || ''}
          placeholder="nama@domain.com"
          onChange={onChange}
          error={errors.email}
        />
        <UploadFile
          previewUrl={previewUrl}
          onChange={onChange}
          error={errors.image}
          fileName={selectedFileName}
        />
      </div>
    </div>
  );
}