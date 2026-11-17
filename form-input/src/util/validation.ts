import { z } from 'zod';

const MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
const ALLOWED_MIME_TYPE = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp'] as const;
const GENDER = ['male', 'female'] as const;
const RELIGION = [
  'islam',
  'kristen_protestan',
  'katolik',
  'hindu',
  'buddha',
  'konghucu',
  'lainnya',
] as const;
const DEGREE_OPTIONS = ['SMK', 'SMA', 'D3', 'S1', 'S2', 'S3'] as const;

export const educationItemSchema = z
  .object({
    schoolName: z
      .string()
      .trim()
      .min(1, 'Nama sekolah/universitas wajib diisi'),

    degree: z.enum(DEGREE_OPTIONS, {
      error: 'Jenjang pendidikan wajib dipilih',
    }),

    major: z
      .string()
      .trim()
      .min(1, 'Jurusan wajib diisi'),

    startDateEducation: z
      .string()
      .min(1, 'Tanggal mulai pendidikan wajib diisi')
      .pipe(z.coerce.date({ error: 'Format tanggal mulai tidak valid' })),

    endDateEducation: z
      .string()
      .min(1, 'Tanggal selesai pendidikan wajib diisi')
      .pipe(z.coerce.date({ error: 'Format tanggal selesai tidak valid' })),
  })
  .refine((data) => data.endDateEducation >= data.startDateEducation, {
    message: 'Tanggal selesai tidak boleh lebih awal dari tanggal mulai',
    path: ['endDateEducation'],
  });

export const educationsSchema = z
  .array(educationItemSchema)
  .min(1, 'Minimal harus mengisi 1 riwayat pendidikan');

export const teacherInputSchema = z.object({
  fullname: z
    .string('Nama lengkap wajib diisi')
    .trim()
    .min(1, 'Minimal terdapat 1 karakter')
    .max(150, 'Maksimal terdapat 150 karakter'),

  // Opsional: gunakan .regex(/^\d{16}$/) jika wajib tepat 16 digit angka
  nuptk: z
    .string()
    .trim()
    .min(1, 'NUPTK wajib diisi')
    .regex(/^\d{16}$/, 'NUPTK harus terdiri dari 16 digit angka'),

  nip: z
    .string()
    .trim()
    .min(1, 'NIP wajib diisi')
    .regex(/^\d{18}$/, 'NIP harus terdiri dari 18 digit angka'),

  nik: z
    .string()
    .trim()
    .min(1, 'NIK wajib diisi')
    .regex(/^\d{16}$/, 'NIK harus terdiri dari 16 digit angka'),

  placeOfBirth: z
    .string()
    .trim()
    .min(1, 'Tempat lahir wajib diisi')
    .max(50, 'Maksimal terdapat 50 karakter'),

  dateBirth: z
    .string()
    .min(1, 'Tanggal lahir wajib diisi')
    .pipe(z.coerce.date({ error: 'Format tanggal lahir tidak valid' })),

  phone: z
    .string()
    .trim()
    .min(1, 'Nomor telepon wajib diisi')
    .regex(/^[0-9]+$/, 'Nomor telepon hanya boleh berisi angka')
    .min(10, 'Nomor telepon minimal 10 digit')
    .max(16, 'Maksimal terdapat 16 karakter'),

  gender: z.enum(GENDER, { error: 'Gender wajib diisi' }),

  religion: z.enum(RELIGION, { error: 'Agama wajib diisi' }),

  email: z
    .string('Email wajib diisi')
    .trim()
    .pipe(z.email('Format email tidak sesuai')),

  image: z
    .custom<File>(
      (val) => val instanceof File && val.size > 0,
      'File foto wajib diunggah'
    )
    .refine((file) => file.size <= MAX_FILE_SIZE, 'Ukuran file maksimal 2MB')
    .refine(
      (file) => (ALLOWED_MIME_TYPE as readonly string[]).includes(file.type),
      'Format yang didukung: JPG, PNG, atau WEBP.'
    ),

  // Gabungkan educationsSchema jika berada dalam satu form yang sama
  educations: educationsSchema,
});

export type EducationItemInput = z.infer<typeof educationItemSchema>;
export type TeacherInputValidation = z.infer<typeof teacherInputSchema>;