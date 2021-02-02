package com.blocker.movieratingsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileDTOTest {

    @Test
    void shouldReturnFileDTO() {
        FileDTO fileDTO = new FileDTO(Models.LONG_VALUE, Models.STRING_VALUE);
        FileDTO otherFileDTO = new FileDTO(Models.LONG_VALUE, Models.STRING_VALUE);

        assertTrue(fileDTO.canEqual(otherFileDTO));
        assertTrue(fileDTO.equals(otherFileDTO));
        assertEquals(fileDTO, otherFileDTO);
        assertEquals(fileDTO.hashCode(), otherFileDTO.hashCode());
    }
}