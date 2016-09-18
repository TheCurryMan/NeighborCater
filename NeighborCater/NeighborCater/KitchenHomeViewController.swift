//
//  KitchenHomeViewController.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import UIKit
import Firebase


class OrderTableViewCell : UITableViewCell {
    

    @IBOutlet weak var foodName: UILabel!
    
    @IBOutlet weak var userName: UILabel!
    
    @IBOutlet weak var userEmail: UILabel!
    
    @IBOutlet weak var userNumber: UILabel!
    
}


class KitchenHomeViewController: UIViewController, UITableViewDataSource {

    @IBOutlet weak var foodName: UITextField!
    
    @IBOutlet weak var foodDescription: UITextField!
    
    @IBOutlet weak var price: UITextField!
    
    var orders = [Order]()
    
    var kitchenUID = String()
    
    @IBOutlet weak var tableView: UITableView!
    
    @IBOutlet weak var segmentedControl: UISegmentedControl!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        var nib = UINib(nibName: "OrderTableViewCell", bundle: nil)
        
        tableView.registerNib(nib, forCellReuseIdentifier: "order")
        
        let ref = FIRDatabase.database().reference()
        let pathRef = ref.child("users").child("\(FIRAuth.auth()!.currentUser!.uid)").child("orders")
        
        let x = pathRef.observeEventType(FIRDataEventType.Value, withBlock: { (snapshot) in
            
            for item in snapshot.children.allObjects as! [FIRDataSnapshot]{
                if let obj = item.value! as? [String:AnyObject] {
                    let order = Order(snapshot: item);
                    self.orders.append(order)
                }
            }})
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func post(sender: AnyObject) {
        let ref = FIRDatabase.database().reference().child("kitchens").child("\(kitchenUID)")
        let kitchen : [String: AnyObject] = ["foodName": foodName.text!,
                                             "foodDescription": foodDescription.text!,
                                             "price": price.text!,
                                             ]
        ref.updateChildValues(kitchen)
        //performSegueWithIdentifier("kitchen", sender: self)
        print("Added item!")
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return orders.count;
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var order = orders[indexPath.row]
        var cell : OrderTableViewCell = tableView.dequeueReusableCellWithIdentifier("order") as! OrderTableViewCell
        cell.foodName.text = order.foodName
        cell.userNumber.text = order.userPhoneNumber
        cell.userEmail.text = order.userEmail
        cell.userName.text = order.userName
        return cell
    }

    @IBAction func switchSegment(sender: AnyObject) {
        switch segmentedControl.selectedSegmentIndex {
        case 0:
            self.tableView.hidden = true;
        default:
            self.tableView.hidden = false;
            self.tableView.reloadData()
        }
    }
  
}
