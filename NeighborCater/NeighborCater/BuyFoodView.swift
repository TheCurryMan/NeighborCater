//
//  BuyFoodView.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import Foundation
import UIKit
import Firebase
import MapKit

class CurrencyTableViewCell : UITableViewCell {
    
    @IBOutlet weak var currencyName: UILabel!
    @IBOutlet weak var currencyValue: UILabel!
    
}

class BuyFoodView : UIView {

    var kitchenSelected = Kitchen(kitchenName: "", kitchenAddress: "", foodName: "", foodDescription: "", latitude: 0.0, longitude: 0.0, user: "", price: "")
    
    var finalUser = [User]()
    @IBOutlet weak var buyNow: UIButton!
    @IBOutlet weak var kitchenName: UILabel!
    
    @IBOutlet weak var ownerName: UILabel!
    @IBOutlet weak var kitchenAddress: UIButton!
    
    
    @IBOutlet weak var foodName: UILabel!
    @IBOutlet weak var foodDescription: UILabel!
    
    @IBOutlet weak var ownerEmail: UIButton!

    @IBOutlet weak var ownerNumber: UIButton!
    
    @IBOutlet weak var priceButton: UIButton!
    
    @IBOutlet weak var currencyTable: UITableView!
    
    var currencyNames = [String]()
    var currencyValues = [Double]()

    func populateData() {
        kitchenName.text = kitchenSelected.kitchenName
        kitchenAddress.setTitle(kitchenSelected.kitchenAddress, forState: UIControlState.Normal)
        foodName.text = kitchenSelected.foodName
        foodDescription.text = kitchenSelected.foodDescription
        priceButton.setTitle("$" + kitchenSelected.price, forState: UIControlState.Normal)
        var postRef = FIRDatabase.database().reference().child("users")
        var refHandle = postRef.observeEventType(FIRDataEventType.Value, withBlock: { (snapshot) in
            for i in snapshot.children.allObjects as! [FIRDataSnapshot] {
                print(i)
                var person = User(snapshot: i)
                if i.key == self.kitchenSelected.user {
                    self.finalUser.append(person)
                    self.ownerName.text = person.userName
                    self.ownerEmail.setTitle(person.userEmail, forState: UIControlState.Normal)
                    self.ownerNumber.setTitle(person.userPhoneNumber + " | " + String(format: "%.2f", self.kitchenSelected.distance) + " mi", forState: UIControlState.Normal)
                }
            }
        })
        
    }
    
    
    @IBAction func openMaps(sender: AnyObject) {
        
            let latitude =  kitchenSelected.latitude
            let longitude =  kitchenSelected.longitude
            
            let regionDistance:CLLocationDistance = 10000
            let coordinates = CLLocationCoordinate2DMake(latitude, longitude)
            let regionSpan = MKCoordinateRegionMakeWithDistance(coordinates, regionDistance, regionDistance)
            let options = [
                MKLaunchOptionsMapCenterKey: NSValue(MKCoordinate: regionSpan.center),
                MKLaunchOptionsMapSpanKey: NSValue(MKCoordinateSpan: regionSpan.span)
            ]
            let placemark = MKPlacemark(coordinate: coordinates, addressDictionary: nil)
            let mapItem = MKMapItem(placemark: placemark)
            mapItem.name = "\(kitchenSelected.kitchenName)"
            mapItem.openInMapsWithLaunchOptions(options)
            
        
    }

    @IBAction func changeCurrency(sender: AnyObject) {
        if self.currencyValues.count < 1 {
   
            let url = NSURL(string: "https://hackthenorth012:Waterloo3043@xecdapi.xe.com/v1/convert_from.json/?from=CAD&to=USD,EUR,JPY,GBP,AUD,CAD,CHF,CNY,MXC,INR&amount=\(kitchenSelected.price)")
            print(kitchenSelected.price)
            print(url)
        
        let task = NSURLSession.sharedSession().dataTaskWithURL(url!) {(data, response, error) in
            do {
                let json = try NSJSONSerialization.JSONObjectWithData(data!, options: .AllowFragments)
                
                if let blogs = json["to"] as? [[String: AnyObject]] {
                    for blog in blogs {
                        self.currencyNames.append(blog["quotecurrency"] as! String)
                        self.currencyValues.append(blog["mid"] as! Double)
                        self.currencyTable.reloadData()
                    }
                    print(self.currencyNames)
                    self.currencyTable.reloadData()
                }
            } catch {
                print("error serializing JSON: \(error)")
            }
            
        }
 
        task.resume()
        print("RESUME TASK")
        self.currencyTable.reloadData()
            
        }
    }
    
    @IBAction func placeOrder(sender: AnyObject) {
        var ref = FIRDatabase.database().reference()
        let key = ref.child("users").child("\(kitchenSelected.user)").child("orders").childByAutoId().key
        let data : [String:AnyObject] = ["foodName": self.foodName.text!,
                    "userName": finalUser[0].userName,
                    "email": finalUser[0].userEmail,
                    "number": finalUser[0].userPhoneNumber]
        let childUpdates = ["/users/\(kitchenSelected.user)/orders/\(key)": data]
        ref.updateChildValues(childUpdates)
    }
    
}