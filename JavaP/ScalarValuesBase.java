import java.util.HashMap;
import java.util.Map;

abstract class ScalarValuesBase {
    protected String doubleStringBase = "double";
    protected String floatStringBase = "float";
    protected String int32StringBase = "int32";
    protected String int64StringBase = "int64";
    protected String uint32StringBase = "uint32";
    protected String uint64StringBase = "uint64";
    protected String sint32StringBase = "sint32";
    protected String sint64StringBase = "sint64";
    protected String fixed32StringBase = "fixed32";
    protected String fixed64StringBase = "fixed64";
    protected String boolStringBase = "bool";
    protected String bytesStringBase = "bytes";

    protected String doubleString;
    protected String floatString;
    protected String int32String;
    protected String int64String;
    protected String uint32String;
    protected String uint64String;
    protected String sint32String;
    protected String sint64String;
    protected String fixed32String;
    protected String fixed64String;
    protected String boolString;
    protected String bytesString;

    protected Map<String, String> scalarValuesMap;

    protected void SetScalarValuesMap() {
        scalarValuesMap = new HashMap<>();
        initializeScalarValuesMap();
    }

    // Scalar değerleri map'e ekleyen metod
    protected void initializeScalarValuesMap() {
        scalarValuesMap.put(doubleString, doubleStringBase);
        scalarValuesMap.put(floatString, floatStringBase);
        scalarValuesMap.put(int32String, int32StringBase);
        scalarValuesMap.put(int64String, int64StringBase);
        scalarValuesMap.put(uint32String, uint32StringBase);
        scalarValuesMap.put(uint64String, uint64StringBase);
        scalarValuesMap.put(sint32String, sint32StringBase);
        scalarValuesMap.put(sint64String, sint64StringBase);
        scalarValuesMap.put(fixed32String, fixed32StringBase);
        scalarValuesMap.put(fixed64String, fixed64StringBase);
        scalarValuesMap.put(boolString, boolStringBase);
        scalarValuesMap.put(bytesString, bytesStringBase);
    }
}