public class Doctor extends User {
    private String medicalLicenseNumber;
    private String specialization;

    // Constructor
    public Doctor(int id, String firstName, String lastName, String email, String password, boolean isDoctor,
            String medicalLicenseNumber, String specialization) {
        super(id, firstName, lastName, email, password, isDoctor);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
    }

    // Getters
    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    // Setters
    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
