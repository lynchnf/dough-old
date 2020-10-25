package norman.dough;

import norman.dough.domain.Acct;
import norman.dough.domain.AcctNbr;
import norman.dough.domain.Cat;
import norman.dough.domain.DataFile;
import norman.dough.domain.DataLine;
import norman.dough.domain.DataTran;
import norman.dough.domain.Pattern;
import norman.dough.domain.Stmt;
import norman.dough.domain.Tran;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FakeDataUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeDataUtil.class);
    private static final Random RANDOM = new Random();
    private static final DateFormat YYMD = new SimpleDateFormat("yyyy-MM-dd");
    private static final String[] NONSENSE_WORDS =
            {"acaer", "ahalty", "aquents", "aturned", "baxtoy", "bilkons", "boycher", "carlds", "corrot", "corsarm",
                    "cortmum", "defas", "deferts", "dialks", "dignate", "distard", "diveher", "expuls", "famongs",
                    "fierer", "gogogox", "griled", "heddies", "hoddlen", "holize", "houshia", "ifringe", "immegap",
                    "lammour", "liledge", "marsons", "merint", "mistial", "morior", "mouruge", "nekmit", "novaly",
                    "oclate", "onama", "partia", "posion", "pricall", "qiameth", "rention", "sementa", "skimat",
                    "skizzle", "specons", "stally", "stictic", "sweeds", "sweeds", "twenbat", "walrie", "winfirn",
                    "yoffa"};

    public static class ReturnTypeAndValue {
        private Class<?> returnType;
        private Object value;

        public ReturnTypeAndValue(Class<?> returnType, Object value) {
            this.returnType = returnType;
            this.value = value;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public Object getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Map<String, String> mainFieldMap = new HashMap<>();
        mainFieldMap.put("Acct", "name");
        mainFieldMap.put("Cat", "name");
        mainFieldMap.put("AcctNbr", "number");
        mainFieldMap.put("Stmt", "closeDate");
        mainFieldMap.put("DataFile", "originalFilename");
        mainFieldMap.put("Pattern", "seq");
        mainFieldMap.put("Tran", "postDate");
        mainFieldMap.put("DataLine", "seq");
        mainFieldMap.put("DataTran", "ofxPostDate");
        List<Acct> acctList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Acct record = new Acct();
            record.setName(nextRandomString(50));
            record.setNickname(nextRandomString(50));
            record.setType(nextRandomString(10));
            record.setAddressName(nextRandomString(50));
            record.setAddress1(nextRandomString(50));
            record.setAddress2(nextRandomString(50));
            record.setCity(nextRandomString(50));
            record.setState(nextRandomString(2));
            record.setZipCode(nextRandomString(10));
            record.setPhoneNumber(nextRandomString(20));
            record.setCreditLimit(nextRandomBigDecimal(100, 99999, 2));
            record.setCronString(nextRandomString(20));
            record.setActive(nextRandomBoolean());
            acctList.add(record);
        }
        List<Cat> catList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Cat record = new Cat();
            record.setName(nextRandomString(100));
            catList.add(record);
        }
        List<AcctNbr> acctNbrList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AcctNbr record = new AcctNbr();
            record.setAcct(nextRandomEntity(acctList));
            record.setNumber(nextRandomString(50));
            record.setEffDate(nextRandomDate(-300, 0));
            acctNbrList.add(record);
        }
        List<Stmt> stmtList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Stmt record = new Stmt();
            record.setAcct(nextRandomEntity(acctList));
            record.setOpenBalance(nextRandomBigDecimal(-9999, 9999, 2));
            record.setCredits(nextRandomBigDecimal(-9999, 9999, 2));
            record.setDebits(nextRandomBigDecimal(-9999, 9999, 2));
            record.setFees(nextRandomBigDecimal(-9999, 9999, 2));
            record.setInterest(nextRandomBigDecimal(-9999, 9999, 2));
            record.setCloseBalance(nextRandomBigDecimal(-9999, 9999, 2));
            record.setMinimumDue(nextRandomBigDecimal(-9999, 9999, 2));
            record.setDueDate(nextRandomDate(-300, 90));
            record.setCloseDate(nextRandomDate(-300, 90));
            stmtList.add(record);
        }
        List<DataFile> dataFileList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataFile record = new DataFile();
            record.setOriginalFilename(nextRandomString(100));
            record.setContentType(nextRandomString(100));
            record.setSize((long) nextRandomInteger(1, 9999));
            record.setUploadTimestamp(nextRandomDate(-300, 90));
            record.setStatus(nextRandomString(10));
            record.setOfxOrganization(nextRandomString(50));
            record.setOfxFid(nextRandomString(10));
            record.setOfxBankId(nextRandomString(10));
            record.setOfxAcctId(nextRandomString(20));
            record.setOfxType(nextRandomString(10));
            record.setAcct(nextRandomEntity(acctList));
            dataFileList.add(record);
        }
        List<Pattern> patternList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Pattern record = new Pattern();
            record.setSeq(nextRandomInteger(1, 9999));
            record.setRegex(nextRandomString(255));
            record.setCat(nextRandomEntity(catList));
            patternList.add(record);
        }
        List<Tran> tranList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tran record = new Tran();
            record.setStmt(nextRandomEntity(stmtList));
            record.setStatus(nextRandomString(10));
            record.setPostDate(nextRandomDate(-300, 90));
            record.setManualPostDate(nextRandomDate(-300, 90));
            record.setUploadedPostDate(nextRandomDate(-300, 90));
            record.setAmount(nextRandomBigDecimal(-9999, 9999, 2));
            record.setManualAmount(nextRandomBigDecimal(-9999, 9999, 2));
            record.setUploadedAmount(nextRandomBigDecimal(-9999, 9999, 2));
            record.setCheckNumber(nextRandomString(10));
            record.setManualCheckNumber(nextRandomString(10));
            record.setUploadedCheckNumber(nextRandomString(10));
            record.setName(nextRandomString(100));
            record.setManualName(nextRandomString(100));
            record.setUploadedName(nextRandomString(100));
            record.setMemo(nextRandomString(100));
            record.setManualMemo(nextRandomString(100));
            record.setUploadedMemo(nextRandomString(100));
            record.setVoided(nextRandomBoolean());
            tranList.add(record);
        }
        List<DataLine> dataLineList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataLine record = new DataLine();
            record.setDataFile(nextRandomEntity(dataFileList));
            record.setSeq(nextRandomInteger(1, 9999));
            record.setText(nextRandomString(255));
            dataLineList.add(record);
        }
        List<DataTran> dataTranList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataTran record = new DataTran();
            record.setDataFile(nextRandomEntity(dataFileList));
            record.setOfxType(nextRandomString(10));
            record.setOfxPostDate(nextRandomDate(-300, 90));
            record.setOfxUserDate(nextRandomDate(-300, 90));
            record.setOfxAmount(nextRandomBigDecimal(-9999, 9999, 2));
            record.setOfxFitId(nextRandomString(50));
            record.setOfxSic(nextRandomString(10));
            record.setOfxCheckNumber(nextRandomString(10));
            record.setOfxCorrectFitId(nextRandomString(10));
            record.setOfxCorrectAction(nextRandomString(10));
            record.setOfxName(nextRandomString(100));
            record.setOfxCategory(nextRandomString(10));
            record.setOfxMemo(nextRandomString(100));
            dataTranList.add(record);
        }

        File file = new File(System.getProperty("user.dir") + "/src/main/resources/import.sql");
        if (file.exists()) {
            String msg = String.format("File %s already exists.", file.getAbsolutePath());
            LOGGER.error(msg);
            throw new RuntimeException(msg);
        }
        try (PrintWriter writer = new PrintWriter(file)) {
            String tableName = null;
            for (Acct record : acctList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!acctList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", acctList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (Cat record : catList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!catList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", catList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (AcctNbr record : acctNbrList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!acctNbrList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", acctNbrList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (Stmt record : stmtList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!stmtList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", stmtList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (DataFile record : dataFileList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!dataFileList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", dataFileList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (Pattern record : patternList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!patternList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", patternList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (Tran record : tranList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!tranList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", tranList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (DataLine record : dataLineList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!dataLineList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", dataLineList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            for (DataTran record : dataTranList) {
                tableName = camelToSnake(record.getClass().getSimpleName());
                printInsert(record, mainFieldMap, writer);
            }
            if (!dataTranList.isEmpty()) {
                String msg = String.format("Successfully wrote %d insert statements for table %s.", dataTranList.size(),
                        tableName);
                LOGGER.info(msg);
            }
            if (writer.checkError()) {
                String msg = String.format("Error occurred while writing to file %s", file.getAbsolutePath());
                LOGGER.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (FileNotFoundException e) {
            String msg = String.format("Error occurred while writing to file %s", file.getAbsolutePath());
            LOGGER.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    private static BigDecimal nextRandomBigDecimal(int unscaledLow, int unscaledHigh, int scale) {
        return BigDecimal.valueOf(RANDOM.nextInt(unscaledHigh - unscaledLow + 1) + unscaledLow, scale);
    }

    private static Boolean nextRandomBoolean() {
        return RANDOM.nextInt(2) == 0;
    }

    private static Date nextRandomDate(int daysLow, int daysHigh) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        int days = RANDOM.nextInt(daysHigh - daysLow + 1) + daysLow;
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    private static int nextRandomInteger(int low, int high) {
        return RANDOM.nextInt(high - low + 1) + low;
    }

    private static String nextRandomString(int limit) {
        StringBuilder randomString = null;
        boolean done = false;
        do {
            String randomWord = NONSENSE_WORDS[RANDOM.nextInt(NONSENSE_WORDS.length)];
            if (randomString == null) {
                if (randomWord.length() > limit) {
                    randomString = new StringBuilder(randomWord.substring(0, limit));
                } else {
                    randomString = new StringBuilder(randomWord);
                }
            } else if (randomString.length() + randomWord.length() + 1 <= limit) {
                randomString.append(" ").append(randomWord);
            } else {
                done = true;
            }
        } while (!done);
        return StringUtils.capitalize(randomString.toString());
    }

    private static <T> T nextRandomEntity(List<T> entityList) {
        return entityList.get(RANDOM.nextInt(entityList.size()));
    }

    private static void printInsert(Object bean, Map<String, String> mainFieldMap, PrintWriter writer) {
        String beanName = bean.getClass().getSimpleName();
        List<Class<?>> simpleTypes =
                Arrays.asList(BigDecimal.class, Boolean.class, Date.class, Integer.class, Long.class, String.class);
        List<String> simpleGetterNames = new ArrayList<>();
        List<String> otherGetterNames = new ArrayList<>();
        for (Method method : bean.getClass().getDeclaredMethods()) {
            String methodName = method.getName();
            if (method.getParameterCount() == 0 && !methodName.equals("getId") &&
                    (methodName.startsWith("is") || methodName.startsWith("get"))) {
                Class<?> returnType = method.getReturnType();
                if (simpleTypes.contains(returnType) || returnType.isEnum()) {
                    simpleGetterNames.add(methodName);
                } else if (!List.class.isAssignableFrom(returnType)) {
                    otherGetterNames.add(methodName);
                }
            }
        }

        String tableName = "`" + camelToSnake(beanName) + "`";
        StringBuilder columnNames = null;
        StringBuilder columnValues = null;

        Collections.sort(simpleGetterNames);
        for (String methodName : simpleGetterNames) {
            columnNames = buildColumnNames(columnNames, methodName);
            columnValues = buildSimpleColumnValues(columnValues, methodName, bean);
        }

        Collections.sort(otherGetterNames);
        for (String methodName : otherGetterNames) {
            columnNames = buildColumnNames(columnNames, methodName + "Id");
            columnValues = buildOtherColumnValues(columnValues, methodName, bean, mainFieldMap);
        }
        writer.printf("INSERT INTO %s (%s)  VALUES (%s);%n", tableName, columnNames.toString(),
                columnValues.toString());
    }

    private static StringBuilder buildColumnNames(StringBuilder columnNames, String methodName) {
        String fieldName;
        if (methodName.startsWith("is")) {
            fieldName = methodName.substring(2);
        } else {
            fieldName = methodName.substring(3);
        }

        String columnName = "`" + camelToSnake(fieldName) + "`";
        columnNames = appendToColumnValues(columnNames, columnName);
        return columnNames;
    }

    private static StringBuilder buildSimpleColumnValues(StringBuilder columnValues, String methodName, Object bean) {
        ReturnTypeAndValue returnTypeAndValue = getReturnTypeAndValue(methodName, bean);
        String columnValue = getSimpleColumnValue(returnTypeAndValue.getReturnType(), returnTypeAndValue.getValue());
        return appendToColumnValues(columnValues, columnValue);
    }

    private static StringBuilder buildOtherColumnValues(StringBuilder columnValues, String methodName, Object bean,
            Map<String, String> mainFieldMap) {
        ReturnTypeAndValue returnTypeAndValue = getReturnTypeAndValue(methodName, bean);
        Class<?> returnType = returnTypeAndValue.getReturnType();
        Object value = returnTypeAndValue.getValue();

        String columnValue;
        if (value == null) {
            columnValue = "NULL";
        } else {
            String entityName = returnType.getSimpleName();
            String tableName = camelToSnake(entityName);
            String mainField = mainFieldMap.get(entityName);
            String mainColumn = camelToSnake(mainField);
            String mainMethodName = "get" + StringUtils.capitalize(mainField);
            ReturnTypeAndValue mainReturnTypeAndValue = getReturnTypeAndValue(mainMethodName, value);
            String mainValue =
                    getSimpleColumnValue(mainReturnTypeAndValue.getReturnType(), mainReturnTypeAndValue.getValue());
            columnValue = "(SELECT `id` FROM `" + tableName + "` WHERE `" + mainColumn + "`=" + mainValue + ")";
        }

        return appendToColumnValues(columnValues, columnValue);
    }

    private static ReturnTypeAndValue getReturnTypeAndValue(String methodName, Object bean) {
        Class<?> returnType = null;
        Object value = null;
        try {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            returnType = method.getReturnType();
            value = method.invoke(bean);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return new ReturnTypeAndValue(returnType, value);
    }

    private static String getSimpleColumnValue(Class<?> returnType, Object value) {
        String columnValue;
        if (value == null) {
            columnValue = "NULL";
        } else if (BigDecimal.class.isAssignableFrom(returnType)) {
            columnValue = String.valueOf(value);
        } else if (Boolean.class.isAssignableFrom(returnType)) {
            columnValue = String.valueOf(value).toUpperCase();
        } else if (Date.class.isAssignableFrom(returnType)) {
            columnValue = "'" + YYMD.format((Date) value) + "'";
        } else if (Integer.class.isAssignableFrom(returnType)) {
            columnValue = String.valueOf(value);
        } else if (Long.class.isAssignableFrom(returnType)) {
            columnValue = String.valueOf(value);
        } else {
            columnValue = "'" + value + "'";
        }
        return columnValue;
    }

    private static StringBuilder appendToColumnValues(StringBuilder columnValues, String columnValue) {
        if (columnValues == null) {
            columnValues = new StringBuilder(columnValue);
        } else {
            columnValues.append(",").append(columnValue);
        }
        return columnValues;
    }

    private static String camelToSnake(String camelStr) {
        String ret = camelStr.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z])([A-Z])", "$1_$2");
        return ret.toLowerCase();
    }
}
