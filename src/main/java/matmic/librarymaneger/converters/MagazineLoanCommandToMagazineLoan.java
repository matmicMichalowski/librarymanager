package matmic.librarymaneger.converters;


import matmic.librarymaneger.commands.MagazineLoanCommand;
import matmic.librarymaneger.model.MagazineLoan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MagazineLoanCommandToMagazineLoan implements Converter<MagazineLoanCommand, MagazineLoan>{

    private final MagazineCommandToMagazine magazineConverter;


    public MagazineLoanCommandToMagazineLoan(MagazineCommandToMagazine magazineConverter) {
        this.magazineConverter = magazineConverter;
    }

    @Nullable
    @Override
    public MagazineLoan convert(MagazineLoanCommand magazineLoanCommand) {
        if(magazineLoanCommand == null) {
            return null;
        }

        final MagazineLoan magazineLoan = new MagazineLoan();

        magazineLoan.setId(magazineLoanCommand.getId());
        magazineLoan.setLoanDate(magazineLoanCommand.getLoanDate());
        magazineLoan.setMagazine(magazineConverter.convert(magazineLoanCommand.getMagazine()));


        return magazineLoan;
    }
}
