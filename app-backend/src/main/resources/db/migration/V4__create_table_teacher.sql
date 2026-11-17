CREATE TABLE IF NOT EXISTS teacher (
    id VARCHAR(36) UNIQUE NOT NULL PRIMARY KEY,
    nuptk VARCHAR(16) UNIQUE,
    nip VARCHAR(18) UNIQUE,
    full_name VARCHAR(150) NOT NULL,
    nik VARCHAR(16) UNIQUE NOT NULL,
    gender VARCHAR(6) CHECK (gender IN ('female', 'male')),
    place_of_birth VARCHAR(100) NOT NULL,
    date_birth DATE NOT NULL,
    religion VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    image VARCHAR(150),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

-- one to many teacher 1-* education_history
CREATE TABLE IF NOT EXISTS education (
    id VARCHAR(36) UNIQUE NOT NULL PRIMARY KEY,
    id_teacher VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    degree VARCHAR(4) CHECK (
        degree IN (
            'smk',
            'sma',
            'd3',
            's1',
            's2',
            's3'
        )
    ),
    major VARCHAR(100) NOT NULL,
    start_date_educate DATE NOT NULL,
    end_date_educate DATE NOT NULL,
    CONSTRAINT fk_teacher_id FOREIGN KEY (id_teacher) REFERENCES teacher (id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE Table IF NOT EXISTS subjects (
    id VARCHAR(36) UNIQUE NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- many to many teacher *-* subjects
CREATE Table IF NOT EXISTS teaching_assigments (
    id VARCHAR(36) UNIQUE NOT NULL PRIMARY KEY,
    id_teacher VARCHAR(36) NOT NULL,
    id_subject VARCHAR(36) NOT NULL,
    academic_year VARCHAR(20) NOT NULL, -- 2026/2027
    semester VARCHAR(10) NOT NULL, -- genap, ganjil
    kelas VARCHAR(50) NOT NULL, -- X-IPA, XI-IPS
    number_of_hours_per_week INT NOT NULL,
    additional_task VARCHAR(150), -- wali kelas, pembina osis
    CONSTRAINT fk_teacher_id FOREIGN KEY (id_teacher) REFERENCES teacher (id) ON DELETE CASCADE,
    CONSTRAINT fk_subjects_id FOREIGN KEY (id_subject) REFERENCES subjects (id) ON DELETE CASCADE
);