package com.ibee.mbordernest.printer.textparser;

import com.ibee.mbordernest.printer.EscPosPrinterCommands;
import com.ibee.mbordernest.printer.exceptions.EscPosConnectionException;
import com.ibee.mbordernest.printer.exceptions.EscPosEncodingException;

public interface IPrinterTextParserElement {
    int length() throws EscPosEncodingException;
    IPrinterTextParserElement print(EscPosPrinterCommands printerSocket) throws EscPosEncodingException, EscPosConnectionException;
}
