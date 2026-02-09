package com.example.capyshop.common.retrofit;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AccessToken {
    private static final String firebaseMessagingScope =
            "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() {
        try {
            String jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"capyshop-5d799\",\n" +
                    "  \"private_key_id\": \"06340195a5c7f458f104848dadbb9047c7dbe010\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDU7StxRurJd7d1\\nwLyIk8Ru2Z7nbyFUbLXz0rmt4z4YD0KJw6wGWOuT6SmEVLA/Z4Un+o5XvmSpbwil\\niVparYTE8f2r45C64huSpW9XEjFYf462uuNnHUmYtm9A3yJzZpitKf1vV5mlEJJK\\n2Z0eIdz3XXu93SIWfvf3aFs4rN/Mh+526dBgC751dh4/o/sK4k4gvLVc3F1bq//z\\nmXZqyXcxp3hCLalvEgUx9bRffb3O4onu8Ybu1YIrt0FSD/AwZeEX3Ub1y8vHI3IO\\nxuf1IqkNL5TGJx8LTQP8mFxQ7UxbMPu1fQgiBX1FSKMvfY0Ksx4EJGhjmitia1qC\\n4cr6EZVhAgMBAAECggEABsFyTBPImz/LIwH3JAuG2KWFnyjYJoo/Hk9PSNnEPJXa\\nB0OdZPjT8KDIim74G0A46f3PH7A7bWIdJMi+LyZWPLwYz6gBYuHi/LzMUuI8N+dE\\nKz60Ea93iFAxfh4WRRG6Ql7qBZWtRZ7IUCD7aoX/MXIBMmhJDpb/XcAtNMJ3ZKZ/\\nSE3BYhvNiubVwyvXOfrjCf/R916NxOM13vg/FY+qZj4rogSYe78Qd3zFuFXk1xo+\\nFtajGl0WcK2nDnPlY41ppnJeDtTENpel7Hw328cF2dyqy0yBCp66WreKehLMF1CW\\nvEJl0Z+DqSlTTC9zoF2DGGPVIh2B/0iZcgGx7X1GqQKBgQDxTfWXe8NQjRMd3y8v\\nE50rAs/u6q2iq3s608rb427K7czK67PVJ4e+lDFB1Pi7Qhrcpq/XpejL/eBPag02\\n2T4lvzTcanNZ7tLTHupTxA07WBmDqJ1n0H+xVMgJpZgKtxqM7XsWZK6kDZ8U1bPt\\nRDOp2lioHQ0dS56+pa4YT1Pm/wKBgQDh5MitdWoKwNxlAM15QPY3m/jtSVNxIihj\\nBPQGPPtNuRKrWEDjQe62a4woXubdqA+DIB0A1C/Vj78s0zW2syXHLsvPwziNVnw4\\n+uy0setJUfRqlYoqkxBXpgVcuu9gvA166ArhkVh/A1bt8wQrKmkH6jxWkOqcEWQI\\nydr66d/jnwKBgQCgzVvhA9enYSuhF26hB4gP9oLMatUk9vNaw3KGz+uwDYIQo7um\\nSv55sNWWUi8i0q2F5wp9QZF+BwOoyDpgF6sDioTWcZXHE5Coogxzn81Vg6MVCpNC\\ncD331VCspQ5J7bUivsrspFGA/5bIUpThgzaOdPPpq9pmcFMsJsp6Zrq5zwKBgAR/\\ngsN5eBAYwQq91lmYwo5e67kF4cR58qXuHlHVeweULNv+mOQtluxI/fR710sIoq5w\\nwEMY9o7hGuRwnDw0Gwd5VhOgBotB8p1L2dTLSi0elahdq++nGAc2xhqPml6pv/b3\\nkefl9gYpcdyxHx8g7BB9SJBMuG89JWnLYzHl4FBvAoGBANkra9csRiG22j2JAM5O\\nC8k8Faq+Yi4M4DskXBxhT+Q760r3pA4qcMbnha55eXHGhnE5QsBhkmhzl9fcRZDt\\nsiMdDpucKniccjwLs5C8X2/IBce5TbP0cM+fAZwD7/+K8bRVhfksOeplaHsopF36\\nYjU78PC3EkabOTht39P+3jfF\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-fbsvc@capyshop-5d799.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"107915927410211104386\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fbsvc%40capyshop-5d799.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}";
            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(firebaseMessagingScope);
            googleCredentials.refresh();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception e) {
            Log.e("AccessToken", "getAccessToken: " + e.getLocalizedMessage());
            return null;
        }
    }

}
