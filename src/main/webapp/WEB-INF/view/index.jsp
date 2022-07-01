<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<html>
<head>
    <title>
        Phils Wider Circle Weather App
    </title>
</head>
<body>
    <form action="/" method="post" >
        <input type="number" name="zipCode">
        <button type="submit">Get Forecast</button>
    </form>
    <hr>
    <table style="border: 1px solid black">
        <tr>
            <th>Zip Code</th>
            <th>Today's High (&deg;F)</th>
            <th>Today's Low (&deg;F)</th>
            <th>Weather Conditions</th>
        </tr>
        <tr style="color:green">
            <td style="border-right:1px solid black">${zip}<br/>${cityState}</td>
            <td style="border-right:1px solid black">${highTemp}</td>
            <td style="border-right:1px solid black">${lowTemp}</td>
            <td>${weatherConditions}<br/><img src="${imgUrl}"/></td>
        </tr>
    </table>
</body>
</html>